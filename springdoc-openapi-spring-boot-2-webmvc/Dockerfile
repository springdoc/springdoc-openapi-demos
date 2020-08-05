FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY ${JAR_FILE} .
ENTRYPOINT ["java","-jar","springdoc-openapi-spring-boot-2-webmvc.jar"]
