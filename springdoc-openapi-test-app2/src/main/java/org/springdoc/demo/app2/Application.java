package org.springdoc.demo.app2;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.springdoc.demo.app2"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public GroupedOpenApi userOpenApi() {
        String[] paths = {"/user/**"};
        String[] packagedToMatch = {"org.springdoc.demo.app2"};
        return GroupedOpenApi.builder().setGroup("users").pathsToMatch(paths).packagesToScan(packagedToMatch).addOpenApiCustomiser(customerGlobalHeaderOpenApiCustomiser())
                .build();
    }

    @Bean
    public GroupedOpenApi storeOpenApi() {
        String[] paths = {"/store/**"};
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
