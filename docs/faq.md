---
layout: default
---
[back](./)

<h1> Welcome to FAQ </h1> 

* TOC
{:toc}

### How can I define multiple OpenAPI definitions in one Spring Boot project?
You can define your own groups of API based on the combination of: API paths and packages to scan. Each group should have a unique `groupName`.
The OpenAPI description of this group, will be available by default on: 

- http://server:port/context-path/v3/api-docs/groupName

To enable the support of multiple OpenAPI definitions, a bean of type `GroupedOpenApi` needs to be defined.

For the following Group definition(based on package path), the OpenAPI description url will be :  /v3/api-docs/**stores**
```java
@Bean
public GroupedOpenApi storeOpenApi() {
	String paths[] = {"/store/**"};
	return GroupedOpenApi.builder().setGroup("stores").pathsToMatch(paths)
			.build();
}
```

For the following Group definition (based on package name), the OpenAPI description url will be:  /v3/api-docs/**users**
```java
@Bean
public GroupedOpenApi userOpenApi() {
	String packagesToscan[] = {"test.org.springdoc.api.app68.api.user"};
	return GroupedOpenApi.builder().setGroup("users").packagesToScan(packagesToscan)
			.build();
}
```

For the following Group definition(based on path), the OpenAPI description url will be:  /v3/api-docs/**pets**
```java
@Bean
public GroupedOpenApi petOpenApi() {
	String paths[] = {"/pet/**"};
	return GroupedOpenApi.builder().setGroup("pets").pathsToMatch(paths)
			.build();
}
```

For the following Group definition (based on package name and path), the OpenAPI description url will be:  /v3/api-docs/**groups**
```java
@Bean
public GroupedOpenApi groupOpenApi() {
	String paths[] = {"/v1/**"};
	String packagesToscan[] = {"test.org.springdoc.api.app68.api.user", "test.org.springdoc.api.app68.api.store"};
	return GroupedOpenApi.builder().setGroup("groups").pathsToMatch(paths).packagesToScan(packagesToscan)
			.build();
}
```

For more details about the usage, you can have a look at the following sample Test:
- https://github.com/springdoc/springdoc-openapi/tree/master/springdoc-openapi-webmvc-core/src/test/java/test/org/springdoc/api/app68

### How can I configure Swagger UI?
- The support of the swagger official properties is available on `springdoc-openapi`.  See [Official documentation](https://swagger.io/docs/open-source-tools/swagger-ui/usage/configuration/).

- You can use the same swagger properties in the documentation as Spring Boot properties.
**NOTE**: All these properties should be declared with the following prefix: `springdoc.swagger-ui`

### How can I filter the resources documented in the output specification by the provided group?
- You can use the standard `swagger-ui` property filter.
```properties
springdoc.swagger-ui.filter=group-a
```

### How can I disable/enable Swagger UI generation based on env variable?
- This property helps you disable only the ui.
```properties
springdoc.swagger-ui.enabled=false
```
### How can I control the default expansion setting for the operations and tags, in the Swagger Ui ,
- You can set this property in your application.yml like so for example:
```properties
springdoc.swagger-ui.doc-expansion= none
```

### How can I change the layout of the `swagger-ui`?
- For layout options, you can use swagger-ui configuration options. For example:
```properties
springdoc.swagger-ui.layout=BaseLayout
```

### How can I sort endpoints alphabetically?
- You can use the following `springdoc-openapi` properties:
```properties
#For sorting endpoints alphabetically
springdoc.swagger-ui.operationsSorter=alpha
#For sorting tags alphabetically
springdoc.swagger-ui.tagsSorter=alpha
```

### How can I disable the try it out button?
- You have to set the following property:
```properties
springdoc.swagger-ui.supportedSubmitMethods="get", "put", "post", "delete", "options", "head", "patch", "trace"
```

### How can I add  Reusable Enums ?
- You should add `@Schema(enumAsRef = true)` on your enum.

### How can I explicitly set which paths to filter?
- You can set list of paths to include using the following property:
```properties
springdoc.pathsToMatch=/v1, /api/balance/**
```

### How can I explicitly set which packages to scan?
- You can set list of packages to include using the following property:
```properties
springdoc.packagesToScan=package1, package2
```

### How can I ignore some field of model ?
- You can use the following annotation on the top of the field that you want to hide:
  - `@Schema(hidden = true)`



### How can I ignore `@AuthenticationPrincipal` parameter from spring-security ?
- A solution workaround would be to use: `@Parameter(hidden = true)`
- For a project that uses `spring-security`, you should add the following dependency, together with the `springdoc-openapi-ui` dependency:

```xml
<dependency>
	<groupId>org.springdoc</groupId>
	<artifactId>springdoc-openapi-security</artifactId>
	<version>last.version</version>
</dependency>
```

### Is there a gradle plugin available?
- There is no `springdoc-gradle-plugin` planned for short term.

### How can I hide a parameter from the documentation ?
- You can use `@Parameter(hidden = true)`

### Is `@Parameters` annotation supported ?
- Yes

### Does `springdoc-openapi` support Jersey?
- If you are using JAX-RS and as implementation Jersey (`@Path` for example), we do not support it.
- We only support exposing Rest Endpoints using Spring managed beans (`@RestController` for example).
- You can have a look at swagger-jaxrs2 project:
  - [https://github.com/swagger-api/swagger-samples/tree/2.0/java/java-jersey2-minimal](https://github.com/swagger-api/swagger-samples/tree/2.0/java/java-jersey2-minimal).

### Can `springdoc-openapi` generate API only for `@RestController`?
- `@RestController` is equivalent to `@Controller` + `@RequestMapping` on the type level.
- For some legacy apps, we are constrained to still support both.
- If you need to hide the `@Controller` on the type level, in this case, you can use: `@Hidden` on controller level.
- Please note this annotation can be also used to hide some methods from the generated documentation.

### Are the following validation annotations supported : `@NotEmpty` `@NotBlank` `@PositiveOrZero` `@NegativeOrZero`?
- Yes

###  How can I map `Pageable` (spring-date-commons) object to correct URL-Parameter in Swagger UI?

The projects that uses `spring-data` should add this dependency together with the `springdoc-openapi-ui` dependency:
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-data-rest</artifactId>
    <version>latest.version</version>
</dependency>
```

If you use Pageable in a GET HTTP method, you will have to declare the explicit mapping of Pageable fields as Query Params and add the @Parameter(hidden = true) Pageable pageable on your pageable parameter.
You should also, declare the annotation `@PageableAsQueryParam` provided by springdoc on the method level, or declare your own if need to define your custom description, defaultvalue, ...


### How can I generate enums in the generated description?
- You could add a property `allowableValues`, to `@Parameter`. For example:

```java
@GetMapping("/example")
public Object example(@Parameter(name ="json", schema = @Schema(description = "var 1",type = "string", allowableValues = {"1", "2"}))
String json) {
   return null;
}
```

- or you could override `toString` on your enum:

```java
@Override
@JsonValue
public String toString() {
	return String.valueOf(action);
}
```

### Does it really support Spring Boot 2.2.x.RELEASE & Hateoas 1.0? 
- Yes

### How can I deploy the Doploy `springdoc-openapi-ui`, behind a reverse proxy?
- You need to make sure the following header is set in your reverse proxy configuration: `X-Forwarded-Prefix`
- For example, using Apache 2, configuration:
```properties
RequestHeader=set X-Forwarded-Prefix "/custom-path"
```
- Then, in your Spring Boot application make sure your application handles this header: `X-Forwarded-For`. There are two ways to achieve this:
```properties
server.use-forward-headers=true
```
- Since Spring Boot 2.2, there is a new property to handle reverse proxy headers:
```properties
server.forward-headers-strategy=framework
```
- Or you can add the following bean to your application:
```java
@Bean
ForwardedHeaderFilter forwardedHeaderFilter() {
   return new ForwardedHeaderFilter();
} 
```

###  Is `@JsonView` annotations in Spring MVC APIs supported?
- Yes

### Adding `springdoc-openapi-ui` dependency breaks my `public/index.html` welcome page
- If you already have static content on your root, and you don't want it to be overridden by `springdoc-openapi-ui` configuration, you can just define a custom configuration of the `swagger-ui`, in order not to override the configuration of your files from in your context-root:
- For example use:
```properties
springdoc.swagger-ui.path= /swagger-ui/api-docs.html
```

### How can I test the Swagger UI?
- You can have a look on this sample test of the ui:
- [https://github.com/springdoc/springdoc-openapi/blob/master/springdoc-openapi-ui/src/test/java/test/org/springdoc/ui/app1/SpringDocApp1Test.java](https://github.com/springdoc/springdoc-openapi/blob/master/springdoc-openapi-ui/src/test/java/test/org/springdoc/ui/app1/SpringDocApp1Test.java).

### How can I customise the OpenAPI object ?
- You can write your own implementation of `OpenApiCustomiser`.
- An example is available on:
  - [https://github.com/springdoc/springdoc-openapi/blob/master/springdoc-openapi-webmvc-core/src/test/java/test/org/springdoc/api/app39/SpringDocApp39Test.java](https://github.com/springdoc/springdoc-openapi/blob/master/springdoc-openapi-webmvc-core/src/test/java/test/org/springdoc/api/app39/SpringDocApp39Test.java).

```java
@Bean
public OpenAPICustomiser consumerTypeHeaderOpenAPICustomiser() {
return openApi -> openApi.getPaths().values().stream().flatMap(pathItem -> pathItem.readOperations().stream())
    .forEach(operation -> operation.addParametersItem(new HeaderParameter().$ref("#/components/parameters/myConsumerTypeHeader")));
}
```

### How to include` spring-boot-actuator` endpoints to Swagger Ui?
- In order to display `spring-boot-actuator` endpoints, you will need to add the following property:
```properties
springdoc.show-actuator=true
```

### How can I return an empty content as response?
- It is be possible to handle as return an empty content as response using, one of the following syntaxes:
  - `content = @Content`
  - `content = @Content(schema = @Schema(hidden = true))`
- For exmaple:
```java
@Operation(summary = "Get thing", responses = {
		@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
		@ApiResponse(responseCode = "404", description = "Not found", content = @Content),
		@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
@RequestMapping(path = "/testme", method = RequestMethod.GET)
ResponseEntity<String> testme() {
	return ResponseEntity.ok("Hello");
}
```

### How are endpoints with multiple consuming media types supported?
- An overloaded method on the same class, with the same HTTP Method and path, will have as a result, only one OpenAPI Operation generated.
- In addition, it's recommended to have the `@Operation` in the level of one of the overloaded methods. Otherwise it might be overridden if it's declared many times within the same overloaded method.

### How can I get yaml and json (OpenAPI) in compile time?
- You can use `springdoc-openapi-maven-plugin` for this functionality:
  - [https://github.com/springdoc/springdoc-openapi-maven-plugin.git](https://github.com/springdoc/springdoc-openapi-maven-plugin.git).
- You can customise the output directory (property outputDir): The default value is: ${project.build.directory}

### What are the ignored types in the documentation?
- `Principal`, `Locale`, `HttpServletRequest` and `HttpServletResponse` and other injectable parameters supported by Spring MVC are excluded.
- Full documentation here:
  - [https://docs.spring.io/spring/docs/5.1.x/spring-framework-reference/web.html#mvc-ann-arguments](https://docs.spring.io/spring/docs/5.1.x/spring-framework-reference/web.html#mvc-ann-arguments).

### How do I add authorization header in requests?
- You should add the `@SecurityRequirement` tags to your protected APIs.
- For example:
```
@Operation(security = { @SecurityRequirement(name = "bearer-key") })
```
- And the security definition sample:

```java
@Bean
 public OpenAPI customOpenAPI() {
   return new OpenAPI()
          .components(new Components()
          .addSecuritySchemes("bearer-key", 
          new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
}
```

### Differentiation to Springfox project

- OAS 3 was released in July 2017, and there was no release of `springfox` to support OAS 3.
`springfox` covers for the moment only swagger 2 integration with Spring Boot. The lastest release date is june 2018. So, in terms of maintenance there is a big lack of support lately.

- We decided to move forward and share the library that we already used on our internal projects, with the community.
- The biggest difference with `springfox`, is that we integrate new features not covered by `springfox`:

  - The integration between Spring Boot and OpenAPI 3 standard.
  - We rely on on `swagger-annotations` and `swagger-ui` only official libraries.
  - We support new features on Spring 5, like `spring-webflux` with annotated controllers.
  - We do our best to answer all the questions and address all issues or enhancement requests

### How do I migrate to OpenAPI 3 with springdoc-openapi
- There is no relation between `springdoc-openapi` and `springfox`.If you want to migrate to OpenAPI 3:
  - Remove all the dependencies and the related code to springfox
  - Add `springdoc-openapi-ui` dependency
- If you don't want to serve the ui from your root path or there is a conflict with an existing configuration, you can just change the following property:
```properties
springdoc.swagger-ui.path=/you-path/swagger-ui.html
```

### How can I set a global header?
- You may have global parameters with Standard OpenAPI description.
- If you need the defintions to appear globally (withing every group), no matter if the group fulfills the conditions specified on the GroupedOpenApi , you can use OpenAPI Bean.
- You can define common parameters under parameters in the global components section and reference them elsewhere via `$ref`. You can also define global header parameters.
- For this, you can override to OpenAPI Bean, and set the global headers or parameters definition on the components level.
```java
@Bean
public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
 return new OpenAPI()
        .components(new Components().addSecuritySchemes("basicScheme", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
        .addParameters("myHeader1", new Parameter().in("header").schema(new StringSchema()).name("myHeader1")).addHeaders("myHeader2", new Header().description("myHeader2 header").schema(new StringSchema())))
        .info(new Info()
        .title("Petstore API")
        .version(appVersion)
        .description("This is a sample server Petstore server. You can find out more about Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/). For this sample, you can use the api key `special-key` to test the authorization filters.")
        .termsOfService("http://swagger.io/terms/")
        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
}
```
### Are Callbacks supported?
- Yes
	
### How can I define SecurityScheme ?
- You can use: `@SecurityScheme` annotation.
- Or you can define it programmatically, by overriding OpenAPI Bean:
```java
 @Bean
 public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
  return new OpenAPI()
	.components(new Components().addSecuritySchemes("basicScheme",
	new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
	info(new Info().title("SpringShop API").version(appVersion)
	.license(new License().name("Apache 2.0").url("http://springdoc.org")));
 }
```

### How can I hide an operation or a controller from documentation ?
- You can use `@io.swagger.v3.oas.annotations.Hidden` annotation at `@RestController`, `@RestControllerAdvice` and method level
- The `@Hidden` annotation on exception handler methods, is considered when building generic (error) responses from `@ControllerAdvice` exception handlers.
- Or use: `@Operation(hidden = true)`




### How to configure global security schemes? 
- For global SecurityScheme, you can add it inside your own OpenAPI definition:
```java
@Bean
public OpenAPI customOpenAPI() {
    return new OpenAPI().components(new Components()
    .addSecuritySchemes("basicScheme", new SecurityScheme()
    .type(SecurityScheme.Type.HTTP).scheme("basic"))).info(new Info().title("Custom API")
    .version("100")).addTagsItem(new Tag().name("mytag"));
}
```
###  Can i use spring property with swagger annotations?
- The support of spring property resolver is possible on all @Info properties
- The support of spring property for description field in swagger-annotations is available on @Operation level
- Its also possible to declare security urls: (openIdConnectUrl - authorizationUrl - refreshUrl - tokenUrl) 

### How can i disable springdoc-openapi cache?
- By default, the OpenAPI description is calculated once, and then cached.
- Sometimes the same swagger-ui is served behind internal and external proxies. some users want the server url, to be computed on each http request.
- In order to disable springdoc cache, you will have to set the following property:
```properties
springdoc.cache.disabled= true
```


###  How is server url generated ?
- Generating automatically server url may be useful, if the documentation is not present.
- If the server annotations are present, they will be used instead.

### How can I expose the api-docs endpoints without using the `swagger-ui`?
- You should use the `springdoc-openapi-core` dependency only:

```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-core</artifactId>
  <version>latest.version</version>
</dependency> 
```
### How can I disable `springdoc-openapi` endpoints ?
- Use the following property:
```
springdoc.api-docs.enabled=false
```

### How can I hide Schema of the the response ?

- To hide the response element, using `@Schema` annotation, as follows, at operation level:
```
@Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))))
```
- Or directly at `@ApiResponses` level:
```
@ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))) })
OR
@ApiResponse(responseCode = "404", description = "Not found", content = @Content)
```

### What is the url of the `swagger-ui`, when I set a different context-path?

- If you use different context-path:
```properties
server.servlet.context-path= /foo
```
- The `swagger-ui` will be available on the following url:
  - http://server:port/foo/swagger-ui.html

### Can I customize OpenAPI object programmatically?

- You can Define your own OpenAPI Bean: If you need the defintions to appear globally (withing every group), no matter if the group fulfills the conditions specified on the GroupedOpenApi , you can use OpenAPI Bean.

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
- If you need the defintions to appear withing a specific group, and respect the conditions specified on the GroupedOpenApi, you can add OpenApiCustomiser to your GroupedOpenApi definition.

```java
GroupedOpenApi.builder().setGroup("users").pathsToMatch(paths).packagesToScan(packagedToMatch).addOpenApiCustomiser(customerGlobalHeaderOpenApiCustomiser())
                .build()

@Bean
public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
	return openApi -> openApi.path("/foo",
	new PathItem().get(new Operation().operationId("foo").responses(new ApiResponses()
	.addApiResponse("default",new ApiResponse().description("")
	.content(new Content().addMediaType("fatz", new MediaType()))))));
}
```


### Where can I find the source code of the demo applications?
- The source code of the application is available at the following github repository:
  - [https://github.com/springdoc/springdoc-openapi-demos.git](https://github.com/springdoc/springdoc-openapi-demos.git).

### Does this library supports annotations from interfaces?
- Yes

### What is the list of the excluded parameter types?
- [https://docs.spring.io/spring/docs/5.1.x/spring-framework-reference/web.html#mvc-ann-arguments](https://docs.spring.io/spring/docs/5.1.x/spring-framework-reference/web.html#mvc-ann-arguments).

### Is file upload supported ?
- The library supports the main file types: `MultipartFile`,  `@RequestPart`, `FilePart`

### Can I use `@Parameter` inside `@Operation` annotation?
- Yes, it's supported

### Why my parameter is marked as required?
- Any `@GetMapping` parameters is marked as required, even if `@RequestParam` is missing. 
- You can add `@Parameter(required=false)` annotation if you need different behaviour.
- Query parameters with `defaultValue` specified are marked as required.

### How are overloaded methods with the same endpoints, but with different parameters
- `springdoc` renders these methods as a single endpoint. It detects the overloaded endpoints, and generates parameters.schema.oneOf.

### What is a proper way to set up Swagger UI to use provided spec.yml?
- With this property, all the springdoc-openapi auto configuration beans are disabled:
```properties
springdoc.api-docs.enabled=false
```
- Then enable the minimal beans configuration, by adding this Bean:
```java
@Bean
SpringDocConfiguration springDocConfiguration(){
	return new SpringDocConfiguration();
}
```
- Then configure, the path of your custom UI yaml file.
```properties
springdoc.swagger-ui.url=/api-docs.yaml
```

[back](./)