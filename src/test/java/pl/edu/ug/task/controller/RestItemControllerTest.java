package pl.edu.ug.task.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestItemControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testResolveItems() {
        RestAssured.given()
                .when().get("/api/items")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(Matchers.not(Matchers.emptyArray()))
                .body("size()", Matchers.greaterThan(0));
    }

    @Test
    public void testFirstItem() {
        RestAssured.given()
                .when().get("/api/items/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("ACER Aspire"))
                .body("date", Matchers.equalTo("2025-01-03"))
                .body("usd", Matchers.equalTo(345.00f)) // should return double but returns float
                .body("pln", Matchers.equalTo(1416.40f));
    }

    @Test
    public void involveNotFound() {
        RestAssured.given()
                .when().get("/api/items/9999")
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body("status", Matchers.equalTo(404))
                .body("title", Matchers.equalTo("Not Found"))
                .body("detail", Matchers.equalTo("Item 9999 not found"));
    }
}
