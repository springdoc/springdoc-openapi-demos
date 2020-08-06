FROM adoptopenjdk:11-jre-hotspot as builder
WORKDIR application
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM adoptopenjdk:11-jre-hotspot
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "-noverify", "-Dspring.main.lazy-initialization=true", "-XX:TieredStopAtLevel=1","-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap" , "-XX:MaxRAMFraction=1" , "-XX:+UseSerialGC", "-XshowSettings:vm" , "-Xss512k", "org.springframework.boot.loader.JarLauncher"]
