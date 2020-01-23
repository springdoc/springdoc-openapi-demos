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

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "org.springdoc.demo.app2" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public GroupedOpenApi userOpenApi() {
		String[] paths = { "/user/**" };
		String[] packagedToMatch = { "org.springdoc.demo.app2" };
		return GroupedOpenApi.builder().setGroup("users").pathsToMatch(paths).packagesToScan(packagedToMatch).addOpenApiCustomiser(customerGlobalHeaderOpenApiCustomiser())
				.build();
	}

	@Bean
	public GroupedOpenApi storeOpenApi() {
		String[] paths = { "/store/**" };
		return GroupedOpenApi.builder().setGroup("stores").pathsToMatch(paths)
				.build();
	}

	@Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
		return openApi -> openApi.path("/foo",
				new PathItem().get(new Operation().operationId("foo").responses(new ApiResponses()
						.addApiResponse("default",
								new ApiResponse()
										.description("")
										.content(new Content().addMediaType("fatz", new MediaType()))))));
	}

}
