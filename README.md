# Currency Exchange

## Description

Project is for only a requested solution by task recruitment. No licensing

## Stack

- Backend:
    - JAVA 21
    - [Gradle 8.13](https://docs.gradle.org/current/userguide/userguide.html)
        - [Spring Boot Plugin](https://spring.io/projects/spring-boot)
        - [Lombok Plugin](https://docs.freefair.io/gradle-plugins/8.13.1/reference/)
        - [Node Gradle Plugin](https://github.com/node-gradle/gradle-node-plugin)
    - [Spring Boot 3](https://spring.io/projects/spring-boot)
    - [Spring Data JPA](https://spring.io/projects/spring-data-jpa) with H2 for coding and testing
      Available Databases:
        - MySQL
        - PostgreSQL
        - MSSQL
        - H2
    - [Project Lombok](https://projectlombok.org/) — Boilerplate code
    - Testing:
        - [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
        - [RestAssured](https://github.com/rest-assured/rest-assured)
- Frontend:
    - [Angular 18](https://v18.angular.dev/)
    - [Angular Material Design 18](https://v18.material.angular.io/)
    - [Node LTS](https://nodejs.org/en)
- Tools:
    - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
    - [Postman](https://www.postman.com/) — for API test

## Usage

### Requirements

- JDK 21+
- Node LTS
- Any SQL database (MySQL, PostgreSQL, MSSQL)

### Prepare a project

1. Configure your database in `application.yaml` changing `spring.datasource.url` property
    - For MySQL: `spring.datasource.url=jdbc:mysql://localhost:3306/currency_exchange?useSSL=false`
    - For PostgreSQL: `spring.datasource.url=jdbc:postgresql://localhost:5432/currency_exchange`
    - For MSSQL: `spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=currency_exchange`
    - For H2 in memory: `spring.datasource.url=jdbc:h2:mem:currency_exchange`
    - For H2 in file: `spring.datasource.url=jdbc:h2:file:./currency_exchange`

   remember specified database `spring.datasource.username` and `spring.datasource.password` if you are use a different
   engine.

2. To build project use `./gradlew build` / to run project use `./gradlew bootRun`. Will automatically build frontend
   and backend too.
3. Open browser and go to [`http://localhost:8080`](http://localhost:8080)
4. For detailed instructions use the question mark icon in the top right corner.

Enjoy
