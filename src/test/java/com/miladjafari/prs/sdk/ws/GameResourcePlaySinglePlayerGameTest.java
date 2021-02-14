package com.miladjafari.prs.sdk.ws;

import com.miladjafari.prs.sdk.dto.CreateGameResponseDto;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
public class GameResourcePlaySinglePlayerGameTest {

    private static final int SYMBOL_PAPER = 1;
    private static final int SYMBOL_ROCK = 2;
    private static final int SYMBOL_SCISSORS = 3;

    private CreateGameResponseDto createGameResponse;

    @BeforeEach
    public void beforeEach() {
        createGameResponse = createNewGame();
    }

    public CreateGameResponseDto createNewGame() {
        return given()
                .when().post("/games/create/singlePlayer")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .extract()
                .as(CreateGameResponseDto.class);
    }

    private String createPlayUrl(CreateGameResponseDto createGameResponse, Integer symbol) {
        return createPlayUrl(createGameResponse.getGameId(), createGameResponse.getPlayerId(), symbol);
    }

    private String createPlayUrl(String gameId, String playerId, Integer symbol) {
        return String.format("games/%s/%s/play/%s", gameId, playerId, symbol);
    }

    @Test
    public void shouldSuccessfullyPlay() {
        given().when()
               .put(createPlayUrl(createGameResponse, SYMBOL_PAPER))
               .then()
               .statusCode(Response.Status.OK.getStatusCode())
               .assertThat()
               .body("equal", notNullValue())
               .body("gameId", equalTo(createGameResponse.getGameId()))
               .body("winnerPlayerId", notNullValue())
               .body("players", hasSize(2));
    }

    @Test
    public void shouldFailPlayWhenSymbolIdIsInvalid() {
        final Integer INVALID_SYMBOL_ID = 6;
        given().when()
               .put(createPlayUrl(createGameResponse, INVALID_SYMBOL_ID))
               .then()
               .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
               .assertThat()
               .body("error", notNullValue())
               .body("error", startsWith("Could find symbol with id"));
    }

    @Test
    public void shouldFailPlayWhenGameIdIsInvalid() {
        final String INVALID_GAME_ID = "INVALID_GAME_ID";
        given().when()
               .put(createPlayUrl(INVALID_GAME_ID, createGameResponse.getPlayerId(), SYMBOL_ROCK))
               .then()
               .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
               .assertThat()
               .body("error", notNullValue())
               .body("error", startsWith("Could not found game with id"));
    }

    @Test
    public void shouldFailPlayWhenPlayerIdIsInvalid() {
        final String INVALID_PLAYER_ID = "INVALID_GAME_ID";
        given().when()
               .put(createPlayUrl(createGameResponse.getGameId(), INVALID_PLAYER_ID, SYMBOL_SCISSORS))
               .then()
               .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
               .assertThat()
               .body("error", notNullValue())
               .body("error", startsWith("Could not found player with PlayerId"));
    }
}