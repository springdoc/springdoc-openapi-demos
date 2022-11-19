package org.springdoc.demo.app4.coffee;

import reactor.core.publisher.Mono;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

@Configuration
public class RouteConfig {
	private final CoffeeService service;

	public RouteConfig(CoffeeService service) {
		this.service = service;
	}

	@Bean
	RouterFunction<ServerResponse> routerFunction() {
		return route().GET("/coffees", this::all, ops -> ops.beanClass(CoffeeService.class).beanMethod("getAllCoffees")).build()
				.and(route().GET("/coffees/{id}", this::byId, ops -> ops.beanClass(CoffeeService.class).beanMethod("getCoffeeById")).build())
				.and(route().GET("/coffees/{id}/orders", this::orders, ops -> ops.beanClass(CoffeeService.class).beanMethod("getOrdersForCoffeeById")).build());
	}

	private Mono<ServerResponse> all(ServerRequest req) {
		return ServerResponse.ok()
				.body(service.getAllCoffees(), Coffee.class);
	}

	private Mono<ServerResponse> byId(ServerRequest req) {
		return ServerResponse.ok()
				.body(service.getCoffeeById(req.pathVariable("id")), Coffee.class);
	}

	private Mono<ServerResponse> orders(ServerRequest req) {
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(service.getOrdersForCoffeeById(req.pathVariable("id")), CoffeeOrder.class);
	}
}
