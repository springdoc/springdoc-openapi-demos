## Spring Security OAuth - New Stack

### Relevant information:

1. `oauth-authorization-server` is a Keycloak Authorization Server wrapped as a Spring Boot application
2. There is one OAuth Client registered in the Authorization Server:
   1. Client Id: newClient
   2. Client secret: newClientSecret
   3. Redirect Uris: 
      - http://localhost:8081/resource-server/swagger-ui/oauth2-redirect.html
      - http://localhost:8082/resource-server/webjars/swagger-ui/oauth2-redirect.html

3. There are two users registered in the Authorization Server:
    1. josh@test.com / 123
    2. dave@test.com / pass
   
4. `oauth-resource-server-webmvc` is a Spring Boot WebMVC based RESTFul API, acting as a backend Application
   swagger-ui:  http://localhost:8081/resource-server/swagger-ui.html

5. `oauth-resource-server-webflux` is a Spring Boot WebFlux based RESTFul API, acting as a backend Application
   swagger-ui:  http://localhost:8082/resource-server/swagger-ui.html

