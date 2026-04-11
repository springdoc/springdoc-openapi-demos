package org.springdoc.demo.mcp.authorizationserver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class MetadataCustomizationFilter extends OncePerRequestFilter {

	private static final String METADATA_PATH = "/.well-known/oauth-authorization-server";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		String path = request.getRequestURI();
		if (!path.equals(METADATA_PATH)) {
			filterChain.doFilter(request, response);
			return;
		}

		ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
		filterChain.doFilter(request, wrappedResponse);

		byte[] body = wrappedResponse.getContentAsByteArray();
		if (body.length > 0 && wrappedResponse.getStatus() == 200) {
			String json = new String(body, StandardCharsets.UTF_8);

			// Add "none" to token_endpoint_auth_methods_supported
			json = json.replace("\"token_endpoint_auth_methods_supported\":[",
					"\"token_endpoint_auth_methods_supported\":[\"none\",");

			// Remove tls_client_certificate_bound_access_tokens
			json = json.replaceAll(",?\"tls_client_certificate_bound_access_tokens\":\\s*true,?", ",");

			// Remove dpop_signing_alg_values_supported
			json = json.replaceAll(",?\"dpop_signing_alg_values_supported\":\\s*\\[[^]]*],?", ",");

			// Clean up any double commas or trailing commas before }
			json = json.replace(",,", ",");
			json = json.replaceAll(",\\s*}", "}");

			byte[] modifiedBody = json.getBytes(StandardCharsets.UTF_8);
			response.setContentLength(modifiedBody.length);
			response.setContentType("application/json");
			response.getOutputStream().write(modifiedBody);
		}
		else {
			wrappedResponse.copyBodyToResponse();
		}
	}

}
