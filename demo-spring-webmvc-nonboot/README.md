# springdoc-openapi demo with non Spring Boot Spring Web MVC

## Building application

### Pre-requisites

- JDK 17+
- Maven 3+

### Build

From the project root:

```sh
mvn -pl demo-spring-webmvc-nonboot -am clean package
```

This will produce:

```text
demo-spring-webmvc-nonboot/target/demo-spring-webmvc-nonboot.war
```


## Run

Deploy the generated WAR to a Servlet container (e.g. Tomcat).

Assuming the context path is /demo-spring-webmvc-nonboot:

- OpenAPI docs: /demo-spring-webmvc-nonboot/v3/api-docs
- Swagger UI: /demo-spring-webmvc-nonboot/swagger-ui/index.html
- Sample API: GET /demo-spring-webmvc-nonboot/api/hello


This is a Maven module.  
Build it together with the other demos using your preferred Maven setup or IDE.
