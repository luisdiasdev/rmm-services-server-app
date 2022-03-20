# rmm-service

## Description

This project aims to build a simple REST API to manage a Remote Monitoring and Management tool.

## Usage

### Prerequisites

- Docker
- docker-compose
- Java 11 JDK

### Running the application

First we need to compile the application and generate the `.jar` file.
```shell
# Windows
.\gradlew clean build
# Linux/Mac
./gradlew clean build
```

After that we can start the docker-compose stack, which will build the Docker image for the database and the application
and start them. It will expose the REST API at port 8080.

```shell
docker-compose up
```

If everything succeeded, you could check the docs [by clicking here.](http://localhost:8080/swagger-ui.html)

### Running tests

To run tests (unit & integration) you can use the following command:
```shell
# Windows
.\gradlew clean test
# Linux/Mac
./gradlew clean test
```
