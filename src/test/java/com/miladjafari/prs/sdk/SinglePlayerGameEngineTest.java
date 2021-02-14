package com.miladjafari.prs.sdk;

import com.miladjafari.prs.sdk.exception.GameRuntimeException;
import com.miladjafari.prs.sdk.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SinglePlayerGameEngineTest {

    private static final String GAME_ID = "GameId";
    private static final String REAL_PLAYER_ID = "MILAD";
    private static final String COMPUTER_PLAYER_ID = "COMPUTER";

    private Player realPlayer;
    private Player computerPlayer;
    private RandomSymbolSelector mockRandomSymbolSelector;

    @BeforeEach
    public void before() {
        realPlayer = new Player(REAL_PLAYER_ID);
        computerPlayer = new Player(COMPUTER_PLAYER_ID);
        mockRandomSymbolSelector = Mockito.mock(RandomSymbolSelector.class);
    }

    private Player createExpectedPlayer(String playerId, Symbol selectedSymbol, boolean increaseScore) {
        Player expectedPlayer = new Player(playerId);
        expectedPlayer.setSymbol(selectedSymbol);
        if (increaseScore) {
            expectedPlayer.increaseScore();
        }
        return expectedPlayer;
    }

    private void assertPlayers(List<Player> expectedPlayers, List<Player> actualPlayers) {
        assertEquals(expectedPlayers.size(), actualPlayers.size());

        expectedPlayers.sort(Comparator.comparing(Player::getId));
        actualPlayers.sort(Comparator.comparing(Player::getId));

        for (int i = 0; i < expectedPlayers.size(); i++) {
            Player expected = expectedPlayers.get(i);
            Player actual = actualPlayers.get(i);

            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getSymbol(), actual.getSymbol());
            assertEquals(expected.getScore(), actual.getScore());
            assertEquals(expected, actual);
        }
    }

    @Test
    public void shouldRealPlayerBecomeWinner() {
        final Symbol REAL_PLAYER_SYMBOL = Symbol.PAPER;
        final Symbol COMPUTER_SYMBOL = Symbol.ROCK;

        Mockito.when(mockRandomSymbolSelector.nextSymbol()).thenReturn(COMPUTER_SYMBOL);

        List<Player> expectedPlayers = new ArrayList<>() {{
            add(createExpectedPlayer(REAL_PLAYER_ID, REAL_PLAYER_SYMBOL, true));
            add(createExpectedPlayer(COMPUTER_PLAYER_ID, COMPUTER_SYMBOL, false));
        }};

        SinglePlayerGameEngine game = new SinglePlayerGameEngine(GAME_ID, realPlayer, computerPlayer, mockRandomSymbolSelector);
        GameResultDto actualGameResult = game.play(REAL_PLAYER_ID, REAL_PLAYER_SYMBOL);

        assertEquals(GAME_ID, actualGameResult.getGameId());
        assertEquals(realPlayer.getId(), actualGameResult.getWinnerPlayerId());
        assertFalse(actualGameResult.isEqual());
        assertPlayers(expectedPlayers, actualGameResult.getPlayers());
    }

    @Test
    public void shouldComputerPlayerBecomeWinner() {
        final Symbol REAL_PLAYER_SYMBOL = Symbol.PAPER;
        final Symbol COMPUTER_SYMBOL = Symbol.SCISSORS;

        Mockito.when(mockRandomSymbolSelector.nextSymbol()).thenReturn(COMPUTER_SYMBOL);

        List<Player> expectedPlayers = new ArrayList<>() {{
            add(createExpectedPlayer(REAL_PLAYER_ID, REAL_PLAYER_SYMBOL, false));
            add(createExpectedPlayer(COMPUTER_PLAYER_ID, COMPUTER_SYMBOL, true));
        }};

        SinglePlayerGameEngine game = new SinglePlayerGameEngine(GAME_ID, realPlayer, computerPlayer, mockRandomSymbolSelector);
        GameResultDto actualGameResult = game.play(REAL_PLAYER_ID, REAL_PLAYER_SYMBOL);

        assertEquals(GAME_ID, actualGameResult.getGameId());
        assertEquals(computerPlayer.getId(), actualGameResult.getWinnerPlayerId());
        assertFalse(actualGameResult.isEqual());
        assertPlayers(expectedPlayers, actualGameResult.getPlayers());
    }

    @Test
    public void shouldGameResultBeEqualWhenComputerAndRealPlayerSymbolsAreTheSame() {
        final Symbol REAL_PLAYER_SYMBOL = Symbol.SCISSORS;
        final Symbol COMPUTER_SYMBOL = Symbol.SCISSORS;

        Mockito.when(mockRandomSymbolSelector.nextSymbol()).thenReturn(COMPUTER_SYMBOL);

        List<Player> expectedPlayers = new ArrayList<>() {{
            add(createExpectedPlayer(REAL_PLAYER_ID, REAL_PLAYER_SYMBOL, false));
            add(createExpectedPlayer(COMPUTER_PLAYER_ID, COMPUTER_SYMBOL, false));
        }};

        SinglePlayerGameEngine game = new SinglePlayerGameEngine(GAME_ID, realPlayer, computerPlayer, mockRandomSymbolSelector);
        GameResultDto actualGameResult = game.play(REAL_PLAYER_ID, REAL_PLAYER_SYMBOL);

        assertEquals(GAME_ID, actualGameResult.getGameId());
        assertTrue(actualGameResult.getWinnerPlayerId().isEmpty());
        assertTrue(actualGameResult.isEqual());
        assertPlayers(expectedPlayers, actualGameResult.getPlayers());
    }

    @Test
    public void shouldThrowAnExceptionIfPlayerIdDoseNotFound() {
        final String INVALID_PLAYER_ID = "INVALID_PLAYER_ID";
        final Symbol REAL_PLAYER_SYMBOL = Symbol.SCISSORS;

        SinglePlayerGameEngine game = new SinglePlayerGameEngine(GAME_ID, realPlayer, computerPlayer, mockRandomSymbolSelector);
        assertThrows(GameRuntimeException.class, () -> game.play(INVALID_PLAYER_ID, REAL_PLAYER_SYMBOL));
    }
}