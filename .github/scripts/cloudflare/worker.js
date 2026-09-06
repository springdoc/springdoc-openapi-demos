// Front door for the demos: authenticates to the private Cloud Run services and proxies by path.
// The services run with --no-allow-unauthenticated, so this worker is the only way in.

const PROJECT_NUMBER = '693302315222';
const REGION = 'europe-west1';

// First path segment -> Cloud Run service name.
const SERVICES = {
	'demo-spring-boot-webmvc': 'demo-spring-boot-webmvc',
	'demo-spring-boot-webflux': 'demo-spring-boot-webflux',
	'demo-spring-boot-webmvc-scalar': 'demo-spring-boot-webmvc-scalar',
	'demo-spring-boot-webflux-scalar': 'demo-spring-boot-webflux-scalar',
	'demo-spring-boot-webflux-functional': 'demo-spring-boot-webflux-functional',
	'spring-cloud-function-webmvc': 'spring-cloud-function-webmvc',
	'spring-cloud-function-webflux': 'spring-cloud-function-webflux',
	'demo-spring-boot-mcp': 'demo-spring-boot-mcp',
	'demo-spring-boot-mcp-authorization-server': 'demo-spring-boot-mcp-authorization-server',
};

// Identity tokens last an hour; keep them in module scope so warm isolates reuse them.
const tokenCache = new Map();

function base64url(input) {
	const bytes = typeof input === 'string' ? new TextEncoder().encode(input) : new Uint8Array(input);
	let binary = '';
	for (const b of bytes) binary += String.fromCharCode(b);
	return btoa(binary).replace(/\+/g, '-').replace(/\//g, '_').replace(/=+$/, '');
}

function pemToArrayBuffer(pem) {
	const body = pem.replace(/-----[^-]+-----/g, '').replace(/\s+/g, '');
	const raw = atob(body);
	const buffer = new Uint8Array(raw.length);
	for (let i = 0; i < raw.length; i++) buffer[i] = raw.charCodeAt(i);
	return buffer.buffer;
}

// Signs a JWT with the service account key and exchanges it for a Cloud Run identity token.
async function mintIdentityToken(credentials, audience) {
	const cached = tokenCache.get(audience);
	if (cached && cached.expiry > Date.now() + 60_000) return cached.token;

	const now = Math.floor(Date.now() / 1000);
	const header = base64url(JSON.stringify({ alg: 'RS256', typ: 'JWT' }));
	const claims = base64url(JSON.stringify({
		iss: credentials.client_email,
		aud: 'https://oauth2.googleapis.com/token',
		iat: now,
		exp: now + 3600,
		target_audience: audience,
	}));

	const key = await crypto.subtle.importKey(
		'pkcs8',
		pemToArrayBuffer(credentials.private_key),
		{ name: 'RSASSA-PKCS1-v1_5', hash: 'SHA-256' },
		false,
		['sign'],
	);
	const signature = await crypto.subtle.sign(
		'RSASSA-PKCS1-v1_5', key, new TextEncoder().encode(`${header}.${claims}`),
	);
	const assertion = `${header}.${claims}.${base64url(signature)}`;

	const response = await fetch('https://oauth2.googleapis.com/token', {
		method: 'POST',
		headers: { 'content-type': 'application/x-www-form-urlencoded' },
		body: new URLSearchParams({
			grant_type: 'urn:ietf:params:oauth:grant-type:jwt-bearer',
			assertion,
		}),
	});
	if (!response.ok) throw new Error(`token exchange failed: ${response.status}`);

	const { id_token } = await response.json();
	tokenCache.set(audience, { token: id_token, expiry: Date.now() + 3_300_000 });
	return id_token;
}

// Works out which service a request belongs to and what path it becomes upstream.
// Normally the first segment names the demo, but an issuer whose URL carries a path
// publishes its metadata at /.well-known/<document>/<path> per RFC 8414, so those
// have to be unwrapped too or discovery lands on the index page.
function resolve(segments, pathname) {
	if (segments[0] === '.well-known' && SERVICES[segments[2]]) {
		const rest = segments.slice(3);
		return {
			service: SERVICES[segments[2]],
			prefix: `/${segments[2]}`,
			path: `/.well-known/${segments[1]}${rest.length ? `/${rest.join('/')}` : ''}`,
		};
	}

	if (!SERVICES[segments[0]]) return null;

	const prefix = `/${segments[0]}`;
	return {
		service: SERVICES[segments[0]],
		prefix,
		// Slicing the raw path rather than rejoining the segments keeps the trailing slash,
		// which Spring treats as a different resource from the bare path.
		path: pathname.slice(prefix.length) || '/',
	};
}

// Maps a Location header back into the public namespace: rewrites the run.app origin
// away and prepends the routing prefix when the app forgot it.
function withPrefix(location, prefix, origin, publicOrigin) {
	let value = location.startsWith(origin) ? location.slice(origin.length) || '/' : location;
	if (value.startsWith(publicOrigin)) value = value.slice(publicOrigin.length) || '/';
	if (!value.startsWith('/') || value.startsWith(`${prefix}/`) || value === prefix) return value;
	return prefix + value;
}

export default {
	async fetch(request, env, ctx) {
		const url = new URL(request.url);

		// The zone plan has no "Always Use HTTPS" toggle we can set from the API token,
		// and the identity token must never travel over a plaintext hop.
		if (url.protocol === 'http:') {
			url.protocol = 'https:';
			return Response.redirect(url.toString(), 301);
		}

		const segments = url.pathname.split('/').filter(Boolean);
		const route = resolve(segments, url.pathname);

		if (!route) {
			const links = Object.keys(SERVICES).map((s) => `<li><a href="/${s}/">${s}</a></li>`).join('');
			return new Response(`<!doctype html><title>springdoc demos</title><ul>${links}</ul>`, {
				headers: { 'content-type': 'text/html; charset=utf-8' },
			});
		}

		// Throttle before touching Cloud Run: a refused request costs no container time.
		const ip = request.headers.get('cf-connecting-ip') ?? 'unknown';
		const { success } = await env.RATE_LIMITER.limit({ key: ip });
		if (!success) {
			return new Response('Too many requests\n', {
				status: 429,
				headers: { 'retry-after': '60', 'content-type': 'text/plain; charset=utf-8' },
			});
		}

		// X-Forwarded-Prefix lets Spring rebuild the public URLs behind the stripped prefix.
		const { service, prefix, path } = route;
		const origin = `https://${service}-${PROJECT_NUMBER}.${REGION}.run.app`;
		const target = new URL(path, origin);
		target.search = url.search;

		const credentials = JSON.parse(env.GCP_SA_KEY);
		const token = await mintIdentityToken(credentials, origin);

		const headers = new Headers(request.headers);
		// Cloud Run consumes X-Serverless-Authorization and does not pass it on, so the
		// caller keeps its own Authorization header. Putting the identity token in
		// Authorization instead makes the mcp demos reject it as a malformed OAuth2 token.
		headers.set('X-Serverless-Authorization', `Bearer ${token}`);
		headers.set('X-Forwarded-Prefix', prefix);
		// Cloud Run strips X-Forwarded-Host, so the public hostname has to travel in the
		// RFC 7239 header; without it swagger-ui advertises the run.app origin instead.
		headers.set('Forwarded', `host=${url.host};proto=https`);

		const upstream = await fetch(new Request(target, {
			method: request.method,
			headers,
			body: request.body,
			redirect: 'manual',
		}));

		// WebFlux builds redirects from the request path and ignores X-Forwarded-Prefix,
		// so a bare /swagger-ui.html would land on the index instead of the demo.
		const location = upstream.headers.get('location');
		if (!location) return upstream;

		const redirected = new Response(upstream.body, upstream);
		redirected.headers.set('location', withPrefix(location, prefix, origin, url.origin));
		return redirected;
	},
};
