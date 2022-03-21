# rmm-service

## Description

This project aims to build a simple REST API for a Remote Monitoring and Management tool.

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
docker-compose up --build
```

If everything succeeded, you could check the API docs [by clicking here.](http://localhost:8080/swagger-ui.html)

### Running tests

Tests already run when you build the `jar` file, but if you want to run them individually check below:

#### Unit Tests

```shell
# Windows
.\gradlew clean test
# Linux/Mac
./gradlew clean test
```

#### Integration Tests

Integration tests use a real-database (powered by *Testcontainers*) and spin up a full *Spring Context*, so it's more close to a real production environment.

```shell
# Windows
.\gradlew clean integrationTest
# Linux/Mac
./gradlew clean integrationTest
```

### Test data

There is already some data loaded via migrations when the application first starts, to make it easier to test.

You can check everything [here](src/main/resources/db/migration)

## Technologies

The technologies/libraries used for this application are:

- Java 11
- Spring Boot (Spring MVC, Spring Data JPA, Spring Security)
- Postgres
- Flyway
- OpenAPI 3 / Swagger UI
- JUnit 5
- Mockito
- AssertJ
- Testcontainers
- Docker
