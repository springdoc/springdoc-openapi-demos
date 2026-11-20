# Cloudflare front door

`demos.springdoc.org` is the only public entry point to the demos. The Cloud Run
services are deployed with `--no-allow-unauthenticated`, so their `run.app` URLs
answer 403 to everyone; the worker holds the `cloudflare-proxy` service account
and is the sole holder of `roles/run.invoker`.

## Layout

| File | Role |
| --- | --- |
| `worker.js` | Mints a Cloud Run identity token, throttles per IP, proxies `/<demo>/...` to the matching service |
| `wrangler.toml` | Worker name, route and the rate-limit binding |
| `springdoc.org.zone` | BIND export of the zone, for the initial Cloudflare import |

Everything in the zone stays DNS-only except `demos`, which must be proxied for
the worker route to fire.

## Deploying

```bash
export CLOUDFLARE_API_TOKEN=...
export CLOUDFLARE_ACCOUNT_ID=e781ec9e3096ee5865bbcf3a456c94b3

wrangler secret put GCP_SA_KEY < ~/.config/gcloud/springdoc-cloudflare-proxy.json
wrangler deploy
```

The token needs `Account:Workers Scripts:Edit`, `Zone:Workers Routes:Edit` and
`Zone:DNS:Edit` on `springdoc.org`. Without the routes permission the script
uploads but the route binding fails, and the hostname stays unrouted.

## Adding a demo

Add the service to the `SERVICES` map in `worker.js` and to the deploy matrix in
`.github/workflows/demos.yml`. The map key is the first path segment.

A demo listed in `SERVED_UNDER_PREFIX` runs under that path on Cloud Run
(`server.servlet.context-path` or `spring.webflux.base-path`, set by the deploy
workflow), so the worker forwards the public path unchanged and the demo only needs
the public host, from the RFC 7239 `Forwarded` header:
`server.request.header.forwarded.strategy=framework` and `naming=rfc-7239`. For the
other demos the worker strips the segment and re-advertises it through
`X-Forwarded-Prefix`.
