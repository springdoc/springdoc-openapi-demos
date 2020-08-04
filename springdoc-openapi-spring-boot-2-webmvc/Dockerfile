FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
EXPOSE 8080
COPY ${JAR_FILE} app.jar
RUN ls -rtla app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
