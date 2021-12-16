package org.acme.hibernate.orm.panache;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.emptyString;

/**
 * @Autor Jairo Nascimento
 * @Created 16/12/2021 - 09:10
 */
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FruitsResourceTest {

    @Test
    @DisplayName("List all, should have all 3 fruits the database has initially:")
    @Order(1)
    public void testListAllFruits() {

        Response response = given()
                .when()
                .contentType("application/json")
                .get("/fruits")
                .then()
                .statusCode(200)
                .extract().response();
        Assertions.assertTrue(response.jsonPath().getList("name")
                .containsAll(List.of("Cherry", "Apple", "Banana")));
    }

    @Test
    @DisplayName("should return a fruits")
    @Order(2)
    public void testGetIdFruits() {

        Response response = given()
                .when()
                .contentType(ContentType.JSON)
                .get("/fruits/1")
                .then()
                .statusCode(200)
                .extract().response();
        var content = response.jsonPath().get("id");
        Assertions.assertEquals(1, content);

    }

    @Test
    @DisplayName("Update Cherry to Pineapple")
    @Order(3)
    public void testPutFruits() {

        given()
                .when()
                .body("{\"name\" : \"Pineapple\"}")
                .contentType("application/json")
                .put("/fruits/1")
                .then()
                .statusCode(200)
                .body(
                        containsString("\"id\":"),
                        containsString("\"name\":\"Pineapple\""));

    }

    @Test
    @DisplayName("Create the Pear")
    @Order(4)
    public void testCreateFruits() {

        given()
                .when()
                .body("{\"name\" : \"Pear\"}")
                .contentType("application/json")
                .post("/fruits")
                .then()
                .statusCode(201)
                .body(
                        containsString("\"id\":"),
                        containsString("\"name\":\"Pear\""));

    }

    @Test
    @DisplayName("Create the Pear")
    @Order(5)
    public void testDeleteFruits() {

        given()
                .when()
                .delete("/fruits/1")
                .then()
                .statusCode(204);

    }

    @Test
    @Order(6)
    @DisplayName("Not found update")
    public void testEntityNotFoundForUpdate() {
        given()
                .when()
                .body("{\"name\" : \"Watermelon\"}")
                .contentType("application/json")
                .put("/fruits/32432")
                .then()
                .statusCode(404)
                .body(emptyString());
    }
}
