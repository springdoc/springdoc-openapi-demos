package org.springdoc.demo.services.gateway;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springdoc.core.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springdoc.core.SwaggerUiConfigProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;

import static org.springdoc.core.Constants.DEFAULT_API_DOCS_URL;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Autowired
	private RouteDefinitionLocator locator;
	
	@Autowired
	private SwaggerUiConfigProperties swaggerUiConfigProperties;
	
	@PostConstruct
	public void init() {
		List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
		Set<SwaggerUrl> urls = new HashSet<>();
		definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service")).forEach(routeDefinition -> {
			String name = routeDefinition.getId().replaceAll("-service", "");
			SwaggerUrl swaggerUrl = new SwaggerUrl(name, DEFAULT_API_DOCS_URL+"/" + name, null);
			urls.add(swaggerUrl);
		});
		swaggerUiConfigProperties.setUrls(urls);
	}
}
