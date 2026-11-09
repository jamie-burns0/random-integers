package me.jamieburns;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

@QuarkusTest
class ConfigResourceTest {

    @ConfigProperty(name = "maximum-number-of-integers-to-generate")
    int maxNumberOfIntegers;

    @Test
    void testConfigEndpoint() {
        given()
          .when().get("/config")
          .then()
             .statusCode(200)
             .contentType(ContentType.JSON)
             .body("success", is(true))
             .body("error", is(nullValue()))
             .body("data.maximumNumberOfIntegers", is(maxNumberOfIntegers));
    }
}
