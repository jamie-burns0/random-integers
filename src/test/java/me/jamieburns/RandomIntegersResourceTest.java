package me.jamieburns;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;


@QuarkusTest
class RandomIntegersResourceTest {

    @Test
    void testGenerateEndpoint() {
        given()
          .when().get("/generate")
          .then()
             .statusCode(200)
             .contentType(ContentType.JSON)
             .body("$", hasSize(100))
             .body("every { it instanceof Integer }", is(true));
    }

}