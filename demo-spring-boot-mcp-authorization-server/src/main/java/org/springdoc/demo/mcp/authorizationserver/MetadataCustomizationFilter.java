package org.springdoc.demo.mcp.authorizationserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
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

	private final ObjectMapper objectMapper = new ObjectMapper();

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
			@SuppressWarnings("unchecked")
			Map<String, Object> metadata = objectMapper.readValue(body, LinkedHashMap.class);

			// Add "none" to token_endpoint_auth_methods_supported
			Object authMethods = metadata.get("token_endpoint_auth_methods_supported");
			if (authMethods instanceof List) {
				@SuppressWarnings("unchecked")
				List<String> methods = new ArrayList<>((List<String>) authMethods);
				if (!methods.contains("none")) {
					methods.add("none");
				}
				metadata.put("token_endpoint_auth_methods_supported", methods);
			}

			// Remove mTLS and DPoP claims that confuse Claude
			metadata.remove("tls_client_certificate_bound_access_tokens");
			metadata.remove("dpop_signing_alg_values_supported");

			byte[] modifiedBody = objectMapper.writeValueAsBytes(metadata);
			response.setContentLength(modifiedBody.length);
			response.setContentType("application/json");
			response.getOutputStream().write(modifiedBody);
		}
		else {
			wrappedResponse.copyBodyToResponse();
		}
	}

}
