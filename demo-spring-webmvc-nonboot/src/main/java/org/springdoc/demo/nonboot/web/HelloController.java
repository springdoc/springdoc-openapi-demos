package org.springdoc.demo.nonboot.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Hello API", description = "Simple demo endpoint for non-boot Spring MVC")
public class HelloController {

    @GetMapping("/api/hello")
    @Operation(summary = "Say hello", description = "Returns a simple greeting")
    public Greeting hello() {
        return new Greeting("Hello from non Spring Boot Spring MVC application!");
    }

    public record Greeting(String message) {
    }
}
