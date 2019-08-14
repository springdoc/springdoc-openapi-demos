package org.springdoc.demo.app1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication
@ComponentScan(basePackages = { "org.springdoc.demo.app1.sample1", "org.springdoc.demo.app1.sample2" })
//@OpenAPIDefinition(info = @Info(title = "the title", version = "v1", description = "My API", license = @License(name = "Apache 2.0", url = "http://foo.bar"), contact = @Contact(url = "http://gigantic-server.com", name = "Fred", email = "Fred@gigagantic-server.com")))
public class Application {

	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("basicScheme",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
				.info(new Info().title("SpringShop API").version(appVersion)
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

}
