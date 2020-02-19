# Migrating from SpringFox

+ Remove springfox and swagger 2 dependencies. Add `springdoc-openapi-ui` dependency instead.

```xml
   <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-ui</artifactId>
      <version>1.2.32</version>
   </dependency>
```
 
+ Replace swagger 2 annotations with swagger 3 annotations (it is already included with `springdoc-openapi-ui` dependency).
Package for swagger 3 annotations is `io.swagger.v3.oas.annotations`.

  - `@ApiParam` -> `@Parameter`
  - `@ApiOperation` -> `@Operation`
  - `@Api` -> `@Tag`
  - `@ApiImplicitParams` -> `@Parameters`
  - `@ApiImplicitParam` -> `@Parameter`
  - `@ApiIgnore` -> `@Parameter(hidden = true)` or `@Operation(hidden = true)` or `@Hidden`
  - `@ApiModel` -> `@Schema` 
  - `@ApiModelProperty` -> `@Schema` 
  

+ This step is optional: Only if you have **multiple** `Docket` beans replace them with `GroupedOpenApi` beans.

  Before:
  ```java
      @Bean
      public Docket publicApi() {
          return new Docket(DocumentationType.SWAGGER_2)
                  .select()
                  .apis(RequestHandlerSelectors.basePackage("org.github.springshop.web.public"))
                  .paths(PathSelectors.regex("/public.*"))
                  .build()
                  .groupName("springshop-public")
                  .apiInfo(apiInfo());
      }
  
      @Bean
      public Docket adminApi() {
          return new Docket(DocumentationType.SWAGGER_2)
                  .select()
                  .apis(RequestHandlerSelectors.basePackage("org.github.springshop.web.admin"))
                  .paths(PathSelectors.regex("/admin.*"))
                  .build()
                  .groupName("springshop-admin")
                  .apiInfo(apiInfo());
      }
  ```
  Now:
  ```java
      @Bean
      public GroupedOpenApi publicApi() {
          return GroupedOpenApi.builder()
                  .setGroup("springshop-public")
                  .pathsToMatch("/public/**")
                  .build();
      }
  
      @Bean
      public GroupedOpenApi adminApi() {
          return GroupedOpenApi.builder()
                  .setGroup("springshop-admin")
                  .pathsToMatch("/admin/**")
                  .build();
      }
  ```
   If you have **only one** `Docket` -- remove it and instead add properties to your `application.properties`:
   ```properties
  springdoc.packagesToScan=package1, package2
  springdoc.pathsToMatch=/v1, /api/balance/**
  ```
+ Add bean of `OpenAPI` type. See example:
  ```java
      @Bean
      public OpenAPI springShopOpenAPI() {
          return new OpenAPI()
                  .info(new Info().title("SpringShop API")
                  .description("Spring shop sample application")
                  .version("v0.0.1")
                  .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                  .externalDocs(new ExternalDocumentation()
                  .description("SpringShop Wiki Documentation")
                  .url("https://springshop.wiki.github.org/docs"));
      }
  ```
  
 + If the swagger-ui is served behind a proxy:
   * [https://springdoc.github.io/springdoc-openapi-demos/faq.html#how-can-i-deploy-the-doploy-springdoc-openapi-ui-behind-a-reverse-proxy](https://springdoc.github.io/springdoc-openapi-demos/faq.html#how-can-i-deploy-the-doploy-springdoc-openapi-ui-behind-a-reverse-proxy).

 + To customise the Swagger UI
   * [https://springdoc.github.io/springdoc-openapi-demos/faq.html#how-can-i-configure-swagger-ui](https://springdoc.github.io/springdoc-openapi-demos/faq.html#how-can-i-configure-swagger-ui).

 + To hide an operation or a controller from documentation
   * [https://springdoc.github.io/springdoc-openapi-demos/faq.html#how-can-i-hide-an-operation-or-a-controller-from-documentation-](https://springdoc.github.io/springdoc-openapi-demos/faq.html#how-can-i-hide-an-operation-or-a-controller-from-documentation-).

