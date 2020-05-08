## Spring Security OAuth - New Stack

### Relevant information:

1. `oauth-authorization-server` is a Keycloak Authorization Server wrapped as a Spring Boot application
2. There is one OAuth Client registered in the Authorization Server:
   1. Client Id: newClient
   2. Client secret: newClientSecret
   3. Redirect Uri: http://localhost:8089/
3. `oauth-resource-server` is a Spring Boot based RESTFul API, acting as a backend Application
   swagger-ui: http://localhost:8081/resource-server/swagger-ui.html
4. There are two users registered in the Authorization Server:
   1. josh@test.com / 123
   2. dave@test.com / pass