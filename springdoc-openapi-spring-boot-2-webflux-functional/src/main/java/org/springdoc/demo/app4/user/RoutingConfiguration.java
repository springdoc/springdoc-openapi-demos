package org.springdoc.demo.app4.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;


@Configuration
public class RoutingConfiguration {

	@Bean
	public RouterFunction<ServerResponse> monoRouterFunction(UserHandler userHandler) {
		return route().GET("/api/user/index", accept(APPLICATION_JSON), userHandler::getAll, ops -> ops.beanClass(UserRepository.class).beanMethod("getAllUsers")).build()
				.and(route().GET("/api/user/{id}", accept(APPLICATION_JSON), userHandler::getUser, ops -> ops.beanClass(UserRepository.class).beanMethod("getUserById")).build()
						.and(route().POST("/api/user/post", accept(APPLICATION_JSON), userHandler::postUser, ops -> ops.beanClass(UserRepository.class).beanMethod("saveUser")).build())
						.and(route().PUT("/api/user/put/{id}", accept(APPLICATION_JSON), userHandler::putUser, ops -> ops.beanClass(UserRepository.class).beanMethod("putUser")).build())
						.and(route().DELETE("/api/user/delete/{id}", accept(APPLICATION_JSON), userHandler::deleteUser, ops -> ops.beanClass(UserRepository.class).beanMethod("deleteUser")).build()));
	}

}