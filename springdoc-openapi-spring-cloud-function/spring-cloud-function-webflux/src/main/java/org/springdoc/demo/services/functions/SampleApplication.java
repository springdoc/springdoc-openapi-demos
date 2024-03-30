package org.springdoc.demo.services.functions;

import java.util.function.Function;
import java.util.function.Supplier;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()
public class SampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("spring-cloud-function-webflux OpenAPI Demo").version(appVersion)
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

	@Bean
	public Function<String, String> reverseString() {
		return value -> new StringBuilder(value).reverse().toString();
	}

	@Bean
	public Function<String, String> uppercase() {
		return String::toUpperCase;
	}

	@Bean
	public Function<Flux<String>, Flux<String>> lowercase() {
		return flux -> flux.map(String::toLowerCase);
	}

	@Bean
	public Supplier<PersonDTO> hello() {
		return PersonDTO::new;
	}

	@Bean
	public Supplier<Flux<String>> words() {
		return () -> Flux.fromArray(new String[] { "foo", "bar" });
	}
}