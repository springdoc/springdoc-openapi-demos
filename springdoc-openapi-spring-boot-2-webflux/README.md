# springdoc-openapi demo with spring-boot-2 web-flux

## Building application

### Pre-requisites
- JDK 8+
- maven 3
- docker CLI

### Option 1: Building Executable JAR
To create an `executable jar`, simply run:

```sh
 mvn clean package
```

### Option 2: Building a non-native OCI Images
To create a non-native OCI docker image, simply run:

```sh
mvn clean spring-boot:build-image
```

### Option 3: Building native image with GraalVM
To create a `native image`, the project rely on spring-native project and buildpacks.
Run the following command

```sh
mvn -Pnative-image clean spring-boot:build-image
```

## Running the native application

To run the demo using docker, invoke the following:

```sh
docker run --rm -p 8080:8080 springdoc-openapi-spring-boot-2-webflux:3.1.6-SNAPSHOT
```


-DspringAot=true -agentlib:native-image-agent=config-output-dir=/Users/bnasslah/Documents/workspace/springdoc-openapi-demos/springdoc-openapi-spring-boot-2-webflux/src/main/resources/META-INF/native-image
