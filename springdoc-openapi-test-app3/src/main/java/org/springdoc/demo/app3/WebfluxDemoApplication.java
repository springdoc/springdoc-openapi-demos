package org.springdoc.demo.app3;

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
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class WebfluxDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxDemoApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicScheme",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(new Info().title("Tweet API").version(appVersion)
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi storeOpenApi() {
        String[] paths = {"/tweets/**"};
        return GroupedOpenApi.builder().setGroup("tweets").pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi userOpenApi() {
        String[] paths = {"/stream/**"};
        String[] packagedToMatch = {"org.springdoc.demo.app3"};
        return GroupedOpenApi.builder().setGroup("stream").pathsToMatch(paths).packagesToScan(packagedToMatch)
                .build();
    }

}
