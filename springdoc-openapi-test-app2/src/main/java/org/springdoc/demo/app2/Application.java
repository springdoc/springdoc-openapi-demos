package org.springdoc.demo.app2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "org.springdoc.demo.app2" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

}
