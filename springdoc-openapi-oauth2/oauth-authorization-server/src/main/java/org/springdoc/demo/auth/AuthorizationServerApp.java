package org.springdoc.demo.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
public class AuthorizationServerApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AuthorizationServerApp.class, args);
    }


}
