package org.springdoc.demo.app4.employee;


import java.util.function.Consumer;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springdoc.core.fn.builders.operation.Builder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class EmployeeFunctionalConfig {


	@Bean
	EmployeeRepository employeeRepository() {
		return new EmployeeRepository();
	}

	@Bean
	RouterFunction<ServerResponse> getAllEmployeesRoute() {
		return route()
				.GET("/employees", accept(MediaType.APPLICATION_JSON),
						findAllEmployeesFunction(), getOpenAPI("findAllEmployees")).build();
	}


	@Bean
	RouterFunction<ServerResponse> getEmployeeByIdRoute() {
		return route().GET("/employees/{id}", findEmployeeByIdFunction(), findEmployeeByIdOpenAPI()).build();
	}


	@Bean
	RouterFunction<ServerResponse> updateEmployeeRoute() {
		return route().POST("/employees/update", accept(MediaType.APPLICATION_XML),
				updateEmployeeFunction(), getOpenAPI("updateEmployee")).build();
	}

	RouterFunction<ServerResponse> composedRoutes() {
		return route().GET("/employees-composed", findAllEmployeesFunction(), getOpenAPI("findAllEmployees")).build()
				.and(route().GET("/employees-composed/{id}", findEmployeeByIdFunction(), findEmployeeByIdOpenAPI()).build())
				.and(route().POST("/employees-composed/update", updateEmployeeFunction(), getOpenAPI("updateEmployee")).build());
	}

	private HandlerFunction<ServerResponse> findAllEmployeesFunction() {
		return req -> ok().body(
				employeeRepository().findAllEmployees(), Employee.class);
	}

	private HandlerFunction<ServerResponse> updateEmployeeFunction() {
		return req -> req.body(BodyExtractors.toMono(Employee.class))
				.doOnNext(employeeRepository()::updateEmployee)
				.then(ok().build());
	}

	private HandlerFunction<ServerResponse> findEmployeeByIdFunction() {
		return req -> ok().body(
				employeeRepository().findEmployeeById(req.pathVariable("id")), Employee.class);
	}

	private Consumer<Builder> getOpenAPI(String findAllEmployees) {
		return ops -> ops.beanClass(EmployeeRepository.class).beanMethod(findAllEmployees);
	}

	private Consumer<Builder> findEmployeeByIdOpenAPI() {
		return ops -> ops.tag("employee")
				.operationId("findEmployeeById").summary("Find purchase order by ID").tags(new String[] { "MyEmployee" })
				.parameter(parameterBuilder().in(ParameterIn.PATH).name("id").description("Employee Id"))
				.response(responseBuilder().responseCode("200").description("successful operation").implementation(Employee.class))
				.response(responseBuilder().responseCode("400").description("Invalid Employee ID supplied"))
				.response(responseBuilder().responseCode("404").description("Employee not found"));
	}

}