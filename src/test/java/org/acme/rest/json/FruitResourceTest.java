package org.acme.rest.json;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import io.quarkus.test.junit.QuarkusTest;
import javax.ws.rs.core.MediaType;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jupiter.MicroShedTest;


@MicroShedTest
@QuarkusTest
@SharedContainerConfig(QuarkusTestEnvironment.class)
public class FruitResourceTest {

    @Test
    @Order(1)
    public void testList() {
        given()
          .when().get("/fruits")
          .then().statusCode(200)
             .body("$.size()", is(3),
                     "name", containsInAnyOrder("Apple", "Cherry", "Banana"),
                     "description", containsInAnyOrder("Gala", "Cavendish", "Black"));
    }

    @Test
    @Order(2)
    public void testAdd() {
        given()
            .body("{\"name\": \"Pear\", \"description\": \"Winter fruit\"}")
            .header("Content-Type", MediaType.APPLICATION_JSON)
        .when().post("/fruits")
        .then().statusCode(200)
            .body("$.size()", is(4),
                    "name", containsInAnyOrder("Apple", "Banana", "Pear", "Cherry"),
                    "description", containsInAnyOrder("Winter fruit", "Gala", "Cavendish", "Black"));

        given()
            .body("{\"name\": \"Pear\", \"description\": \"Winter fruit\"}")
            .header("Content-Type", MediaType.APPLICATION_JSON)
        .when().delete("/fruits")
        .then().statusCode(200)
            .body("$.size()", is(3),
                "name", containsInAnyOrder("Apple", "Cherry", "Banana"),
                "description", containsInAnyOrder("Gala", "Cavendish", "Black"));
    }
}
