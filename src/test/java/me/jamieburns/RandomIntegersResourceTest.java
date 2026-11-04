package me.jamieburns;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
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
             .body("$", hasSize(n))
             .body("every { it instanceof Integer }", is(true));
    }

}