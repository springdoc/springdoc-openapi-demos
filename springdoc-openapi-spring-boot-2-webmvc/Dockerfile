FROM openjdk:8-jre-slim
RUN addgroup   --system --gid 1001 spring && adduser  --system --uid  1001   --group spring
USER spring:spring
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
