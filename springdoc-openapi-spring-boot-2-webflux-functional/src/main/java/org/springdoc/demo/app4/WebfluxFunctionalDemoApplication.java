/*
 *
 *  * Copyright 2019-2020 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.springdoc.demo.app4;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebfluxFunctionalDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxFunctionalDemoApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("basicScheme",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
				.info(new Info().title("Coffee/employee/user API").version(appVersion)
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}


	@Bean
	public GroupedOpenApi employeesOpenApi() {
		String[] paths = { "/employees/**" };
		return GroupedOpenApi.builder().group("employees").pathsToMatch(paths)
				.build();
	}

	@Bean
	public GroupedOpenApi userOpenApi() {
		String[] paths = { "/api/user/**" };
		return GroupedOpenApi.builder().group("users").pathsToMatch(paths)
				.build();
	}

	@Bean
	public GroupedOpenApi coffeeOpenApi() {
		String[] paths = { "/coffees/**" };
		return GroupedOpenApi.builder().group("coffees").pathsToMatch(paths)
				.build();
	}

}
