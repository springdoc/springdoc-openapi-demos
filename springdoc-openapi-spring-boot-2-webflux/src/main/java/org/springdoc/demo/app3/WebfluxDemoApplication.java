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

package org.springdoc.demo.app3;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebfluxDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxDemoApplication.class, args);
	}

	@Bean
	public GroupedOpenApi tweetsOpenApi(@Value("${springdoc.version}") String appVersion) {
		String[] paths = { "/tweets/**" };
		return GroupedOpenApi.builder().
				group("tweets")
				.addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Tweets API").version(appVersion)))
				.pathsToMatch(paths)
				.build();
	}

	@Bean
	public GroupedOpenApi streamOpenApi(@Value("${springdoc.version}") String appVersion) {
		String[] paths = { "/stream/**" };
		String[] packagedToMatch = { "org.springdoc.demo.app3" };
		return GroupedOpenApi.builder().group("x-stream")
				.addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Stream API").version(appVersion)))
				.pathsToMatch(paths).packagesToScan(packagedToMatch)
				.build();
	}

}
