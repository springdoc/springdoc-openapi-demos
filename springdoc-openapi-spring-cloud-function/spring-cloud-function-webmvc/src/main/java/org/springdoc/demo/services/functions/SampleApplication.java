package org.springdoc.demo.services.functions;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication()
public class SampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("spring-cloud-function-webmvc OpenAPI Demo").version(appVersion)
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
	@RouterOperations({
			@RouterOperation(method = RequestMethod.GET, operation = @Operation(description = "Say hello GET", operationId = "lowercaseGET", tags = "positions",
					responses = @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))))),
			@RouterOperation(method = RequestMethod.POST, operation = @Operation(description = "Say hello POST", operationId = "lowercasePOST", tags = "positions",
					responses = @ApiResponse(responseCode = "200", description = "new desc", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class))))))
	})
	public Function<List<String>, List<String>> lowercase() {
		return list -> list.stream().map(String::toLowerCase).collect(Collectors.toList());
	}

	@Bean
	@RouterOperation(operation = @Operation(description = "Say hello By Id", operationId = "hellome", tags = "persons",
			responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDTO.class)))))
	public Supplier<PersonDTO> helloSupplier() {
		return PersonDTO::new;
	}

	@Bean
	public Consumer<PersonDTO> helloConsumer() {
		return PersonDTO::getFirstName;
	}

	@Bean
	public Supplier<List<String>> words() {
		return () -> Arrays.asList("foo", "bar");
	}
}