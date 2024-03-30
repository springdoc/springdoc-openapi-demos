# springdoc-openapi demo with spring-boot-2 hateoas

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

## Running the native application

To run the demo using docker, invoke the following:

```sh
docker pull springdocdemos/springdoc-openapi-hateoas-service:latest
docker run --rm -p 8080:8080 springdocdemos/springdoc-openapi-hateoas-service:latest
```