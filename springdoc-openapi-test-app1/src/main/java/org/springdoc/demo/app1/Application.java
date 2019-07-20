package org.springdoc.demo.app1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@ComponentScan(basePackages = { "org.springdoc.demo.app1.sample1", "org.springdoc.demo.app1.sample2" })
@OpenAPIDefinition(info = @Info(title = "the title", version = "0.0", description = "My API", license = @License(name = "Apache 2.0", url = "http://foo.bar"), contact = @Contact(url = "http://gigantic-server.com", name = "Fred", email = "Fred@gigagantic-server.com")))
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}


}
