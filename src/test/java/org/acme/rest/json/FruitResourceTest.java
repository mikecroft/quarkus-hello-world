package org.acme.rest.json;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class FruitResourceTest {

    @Test
    public void testList() {
        given()
                .when().get("/fruits")
                .then()
                .statusCode(200)
                .body("$.size()", is(4),
                        "name", containsInAnyOrder("Apple", "Banana", "Cherry", "Pineapple"),
                        "description", containsInAnyOrder("Winter fruit", "Tropical fruit", "Summer fruit", "Tropical fruit"));
    }

    @Test
    public void testAdd() {
        // given()
        //         .body("{\"name\": \"Pear\", \"description\": \"Winter fruit\"}")
        //         .header("Content-Type", ContentType.JSON)
        //         .when()
        //         .post("/fruits")
        //         .then()
        //         .statusCode(200)
        //         .body("$.size()", is(5),
        //                 "name", containsInAnyOrder("Apple", "Pineapple", "Banana", "Cherry", "Pear"),
        //                 "description", containsInAnyOrder("Winter fruit", "Tropical fruit", "Summer fruit", "Tropical fruit"));

        // given()
        //         .body("{\"name\": \"Apple\", \"description\": \"Winter fruit\"}")
        //         .header("Content-Type", ContentType.JSON)
        //         .when()
        //         .delete("/fruits")
        //         .then()
        //         .statusCode(200)
        //         .body("$.size()", is(4),
        //                 "name", containsInAnyOrder("Apple", "Pineapple"),
        //                 "description", containsInAnyOrder("Winter fruit", "Tropical fruit", "Summer fruit", "Tropical fruit"));


    }
}
