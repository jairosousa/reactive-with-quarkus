package org.acme.hibernate.orm.panache;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.acme.hibernate.orm.panache.entity.Fruit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;

/**
 * @Autor Jairo Nascimento
 * @Created 16/12/2021 - 09:10
 */
@QuarkusTest
public class FruitsResourceTest {

    @Test
    @DisplayName("Deve retornar lista de nomes")
    public void testListAllFruits() {

        Response response = given()
                .when()
                .get("/fruits")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        Assertions.assertTrue(response.jsonPath().getList("name")
                .containsAll(List.of("Cherry", "Apple", "Banana")));
    }

    @Test
    @DisplayName("Deve retornar Fruit")
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
}
