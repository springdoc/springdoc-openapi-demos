package org.springdoc.demo.services.gateway;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springdoc.core.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springdoc.core.SwaggerUiConfigProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import static org.springdoc.core.Constants.DEFAULT_API_DOCS_URL;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	@Lazy(false)
	public List<GroupedOpenApi> apis(RouteDefinitionLocator locator, SwaggerUiConfigParameters swaggerUiConfigParameters) {
		List<GroupedOpenApi> groups = new ArrayList<>();
		//Set<SwaggerUrl> urls = new HashSet<>();
		List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
		for (RouteDefinition definition : definitions) {
			System.out.println("id: " + definition.getId() + "  " + definition.getUri().toString());
		}
		definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service")).forEach(routeDefinition -> {
			String name = routeDefinition.getId().replaceAll("-service", "");
			GroupedOpenApi groupedOpenApi =GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();
			groups.add(groupedOpenApi);
			SwaggerUrl swaggerUrl = new SwaggerUrl(name, DEFAULT_API_DOCS_URL+"/" + name, null);
		//	urls.add(swaggerUrl);
		});
	//	swaggerUiConfigParameters.setUrls(urls);
		return groups;
	}
}
