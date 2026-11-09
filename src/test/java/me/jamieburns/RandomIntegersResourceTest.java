package me.jamieburns;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;

import org.eclipse.microprofile.config.inject.ConfigProperty;


@QuarkusTest
class RandomIntegersResourceTest {

    @ConfigProperty(name = "maximum-number-of-integers-to-generate")
    int maxNumberOfIntegers;

    @Test
    void testGenerateEndpoint() {
        var n = maxNumberOfIntegers - 1;
        given()
          .queryParam("numberOfIntegers", n)
          .when().get("/generate")
          .then()
             .statusCode(200)
             .contentType(ContentType.JSON)
             .body("success", is(true))
             .body("error", is(nullValue()))
             .body("data", hasSize(n))
             .body("data.every { it instanceof Integer }", is(true));
    }

    @Test
    void testGenerateWithNegativeNumberReturns400() {
        given()
          .queryParam("numberOfIntegers", -1)
          .when().get("/generate")
          .then()
             .statusCode(400)
             .contentType(ContentType.JSON)
             .body("success", is(false))
             .body("error", is("numberOfIntegers must be at least 1"))
             .body("data", is(nullValue()));
    }

    @Test
    void testGenerateWithZeroReturns400() {
        given()
          .queryParam("numberOfIntegers", 0)
          .when().get("/generate")
          .then()
             .statusCode(400)
             .contentType(ContentType.JSON)
             .body("success", is(false))
             .body("error", is("numberOfIntegers must be at least 1"))
             .body("data", is(nullValue()));
    }

    @Test
    void testGenerateWithNumberGreaterThanMaxReturns400() {
        given()
          .queryParam("numberOfIntegers", maxNumberOfIntegers + 1)
          .when().get("/generate")
          .then()
             .statusCode(400)
             .contentType(ContentType.JSON)
             .body("success", is(false))
             .body("error", is("numberOfIntegers must be no more than " + maxNumberOfIntegers))
             .body("data", is(nullValue()));
    }

}