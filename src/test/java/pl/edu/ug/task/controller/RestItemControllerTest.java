package pl.edu.ug.task.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import pl.edu.ug.task.service.CurrencyExchange;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestItemControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private CurrencyExchange exchange;

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
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("First Item"))
                .body("date", Matchers.equalTo("First Item"))
                .body("price_usd", Matchers.equalTo(1.00))
                .body("price_pln", Matchers.equalTo(1.00))
                .contentType(ContentType.JSON);
    }

    @Test
    public void involveNotFound() {
        RestAssured.given()
                .when().get("/api/items/9999")
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON);
    }
}
