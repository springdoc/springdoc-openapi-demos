FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]	ENTRYPOINT ["java","-jar","/app.jar"]
