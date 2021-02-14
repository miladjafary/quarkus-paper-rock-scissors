package com.miladjafari.prs.ws;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class GameResourceCreateSinglePlayerGameTest {

    @Test
    public void shouldCreateSuccessfullyNewGame() {
        given()
                .when().post("/games/create/singlePlayer")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .assertThat()
                .body("gameId", notNullValue())
                .body("playerId", notNullValue())
                .body("url", notNullValue());
    }
}