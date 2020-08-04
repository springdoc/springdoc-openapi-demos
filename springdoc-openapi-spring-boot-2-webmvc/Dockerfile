FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY target/springdoc-openapi-spring-boot-2-webmvc.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
