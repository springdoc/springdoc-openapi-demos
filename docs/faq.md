---
layout: default
---
# Welcome to FAQ

### What is the url of the swagger-ui, when i set a different context-path?

If you use different context-path:
```properties
server.servlet.context-path= /foo
```
The swagger-ui will be available on the following url:
- http://server:port/foo/swagger-ui.html

### Can i  customize OpenAPI object programmatically?

You can Define your own OpenAPI Bean:
```java
@Bean
public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
	return new OpenAPI()
    .components(new Components().addSecuritySchemes("basicScheme",
            new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
    .info(new Info().title("SpringShop API").version(appVersion)
            .license(new License().name("Apache 2.0").url("http://springdoc.org")));
}
```

### Where can i find the source code of the demo applications:
The source code of the application is available at the following github repository:
- https://github.com/springdoc/springdoc-openapi-demos.git

### Does this library supports annotations from interfaces?
- Yes

### What is the list of the excluded parameter types :
- https://docs.spring.io/spring/docs/5.1.x/spring-framework-reference/web.html#mvc-ann-arguments

### Is file upload supported ?
- The library supports the main file types: MultipartFile 

### Can i use @Parameter inside @Operation annotation ?
- Yes, its supported

### Why my parameter is marked as required ?
- Any @GetMapping parameters is marked as required, even if @RequestParam missing. 
- You can add @Parameter(required=false) annotation if you need different behaviour

### How are overloaded methods with the same endpoints, but with different parameters
- springdoc renders these methods as a single endpoint. It detects the overloaded endpoints, and generates parameters.schema.oneOf.

[back](./)