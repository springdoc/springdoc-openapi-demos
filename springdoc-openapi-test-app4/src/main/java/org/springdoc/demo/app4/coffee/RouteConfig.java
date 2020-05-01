package org.springdoc.demo.app4.coffee;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.*;

import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.webflux.annotations.RouterOperation;
import org.springdoc.webflux.annotations.RouterOperations;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

@Configuration
public class RouteConfig {
    private final CoffeeService service;

    public RouteConfig(CoffeeService service) {
        this.service = service;
    }



	@Bean
	@RouterOperations({ @RouterOperation(path = "/coffees", method = RequestMethod.GET, beanClass = CoffeeService.class, beanMethod = "getAllCoffees"),
			@RouterOperation(path = "/coffees/{id}", method = RequestMethod.GET, beanClass = CoffeeService.class, beanMethod = "getCoffeeById"),
			@RouterOperation(path = "/coffees/{id}/orders", method = RequestMethod.GET, beanClass = CoffeeService.class, beanMethod = "getOrdersForCoffeeById") })
    RouterFunction<ServerResponse> routerFunction() {
        return route(GET("/coffees"), this::all)
                .andRoute(GET("/coffees/{id}"), this::byId)
                .andRoute(GET("/coffees/{id}/orders"), this::orders);
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
