plugins {
    application
    id("io.freefair.lombok") version "8.13.1" // lombok plugin for boilerplate and delombok for documentation compile
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "pl.edu.ug.task"
version = "0.1.0-SNAPSHOT"

java { // using Java 21
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

application {
    mainClass = "pl.edu.ug.task.App"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // using JPA
    implementation("org.springframework.boot:spring-boot-starter-data-rest") // using Data REST to create an api request
    implementation("org.springframework.boot:spring-boot-starter-web") // base of spring boot web with reactive streams
    implementation("org.springframework.boot:spring-boot-starter-validation") // using validation data
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf") // using Thymeleaf Templates
    runtimeOnly("org.springframework.boot:spring-boot-starter-actuator") // for debugging in IDE
    implementation("com.h2database:h2")
    implementation("com.mysql:mysql-connector-j")
    implementation("org.postgresql:postgresql")
    implementation("com.microsoft.sqlserver:mssql-jdbc")
    testImplementation("org.springframework.boot:spring-boot-starter-test")// for testing spring endpoints
    testImplementation("io.rest-assured:rest-assured")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher") // JUnit 5
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
    processResources {
        dependsOn(":web:build")
    }
    clean {
        dependsOn(":web:clean")
    }
}
