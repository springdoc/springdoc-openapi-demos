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

package org.springdoc.demo.app2;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.OperationCustomizer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import static org.springdoc.core.Constants.ALL_PATTERN;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}


	@Bean
	@Profile("!prod")
	public GroupedOpenApi actuatorApi(OpenApiCustomiser actuatorOpenApiCustomiser, OperationCustomizer actuatorCustomizer, WebEndpointProperties endpointProperties) {
		return GroupedOpenApi.builder()
				.group("Actuator")
				.pathsToMatch(endpointProperties.getBasePath()+ ALL_PATTERN)
				.addOpenApiCustomiser(actuatorOpenApiCustomiser)
				.addOperationCustomizer(actuatorCustomizer)
				.pathsToExclude("/health/*")
				.build();
	}

	@Bean
	public GroupedOpenApi usersGroup() {
		return GroupedOpenApi.builder().group("users")
				.addOperationCustomizer((operation, handlerMethod) -> {
					operation.addSecurityItem(new SecurityRequirement().addList("basicScheme"));
					return operation;
				})
				.packagesToScan("org.springdoc.demo.app2")
				.build();
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("basicScheme",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
				.info(new Info().title("Petstore API").version(appVersion).description(
						"This is a sample server Petstore server.  You can find out more about     Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/).      For this sample, you can use the api key `special-key` to test the authorization     filters.")
						.termsOfService("http://swagger.io/terms/")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
}
