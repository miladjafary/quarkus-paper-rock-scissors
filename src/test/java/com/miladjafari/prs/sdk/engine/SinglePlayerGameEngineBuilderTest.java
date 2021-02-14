package com.miladjafari.prs.sdk.engine;

import com.miladjafari.prs.sdk.exception.GameRuntimeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SinglePlayerGameEngineBuilderTest {

    private static final String GAME_ID = "GameId";
    private static final String REAL_PLAYER_ID = "MILAD";
    private static final String COMPUTER_PLAYER_ID = "COMPUTER";

    @Test
    public void shouldBuildGameEngineSuccessfully() {
        SinglePlayerGameEngine actualEngine = new SinglePlayerGameEngineBuilder()
                .gameId(GAME_ID)
                .playerId(REAL_PLAYER_ID)
                .build();

        assertNotNull(actualEngine);
    }

    @Test
    public void shouldThrowExceptionWhenGameIdIsNotSetOrSetByEmptyString() {
        final String EMPTY_STRING = " ";
        assertThrows(GameRuntimeException.class, () -> new SinglePlayerGameEngineBuilder()
                .gameId(EMPTY_STRING)
                .playerId(REAL_PLAYER_ID)
                .build());

        assertThrows(GameRuntimeException.class, () -> new SinglePlayerGameEngineBuilder()
                .playerId(REAL_PLAYER_ID)
                .build());

    }

    @Test
    public void shouldThrowExceptionWhenRealPlayerIdIsNotSetOrSetByEmptyString() {
        final String EMPTY_STRING = " ";
        assertThrows(GameRuntimeException.class, () -> new SinglePlayerGameEngineBuilder()
                .gameId(GAME_ID)
                .playerId(EMPTY_STRING)
                .build());

        assertThrows(GameRuntimeException.class, () -> new SinglePlayerGameEngineBuilder()
                .gameId(GAME_ID)
                .build());

    }
}