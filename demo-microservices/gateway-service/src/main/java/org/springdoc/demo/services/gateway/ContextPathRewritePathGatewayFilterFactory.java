package org.springdoc.demo.services.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

/**
 * @author bnasslahsen
 */
@Component
public class ContextPathRewritePathGatewayFilterFactory extends RewritePathGatewayFilterFactory {

	@Override
	public GatewayFilter apply(Config config) {
		String replacement = config.getReplacement().replace("$\\", "$");
		return (exchange, chain) -> {
			ServerHttpRequest req = exchange.getRequest();

			addOriginalRequestUrl(exchange, req.getURI());
			String path = req.getURI().getRawPath();

			String newPath = path.replaceAll(config.getRegexp(), replacement);
			ServerHttpRequest request = req.mutate().path(newPath).contextPath("/").build();

			exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, request.getURI());

			return chain.filter(exchange.mutate().request(request).build());
		};
	}

}