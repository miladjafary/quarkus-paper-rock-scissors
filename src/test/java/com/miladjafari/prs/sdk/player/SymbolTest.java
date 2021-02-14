package com.miladjafari.prs.sdk.player;

import com.miladjafari.prs.sdk.exception.GameRuntimeException;
import org.junit.jupiter.api.Test;

import static com.miladjafari.prs.sdk.player.Symbol.PAPER;
import static com.miladjafari.prs.sdk.player.Symbol.ROCK;
import static com.miladjafari.prs.sdk.player.Symbol.SCISSORS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SymbolTest {

    @Test
    public void paperShouldBeatsRockAndShouldNotBeatsScissors() {
        assertTrue(PAPER.beats(ROCK));
        assertFalse(PAPER.beats(SCISSORS));
    }

    @Test
    public void rockShouldBeatsScissorsAndShouldNotBeatsPaper() {
        assertTrue(ROCK.beats(SCISSORS));
        assertFalse(ROCK.beats(PAPER));
    }

    @Test
    public void scissorsShouldBestsPaperAndShouldNotBeatsRock() {
        assertTrue(SCISSORS.beats(PAPER));
        assertFalse(SCISSORS.beats(ROCK));
    }

    @Test
    public void shouldSuccessfullyGetSymbolById() {
        assertEquals(PAPER, Symbol.getSymbolById(1));
        assertEquals(ROCK, Symbol.getSymbolById(2));
        assertEquals(SCISSORS, Symbol.getSymbolById(3));
    }

    @Test
    public void shouldThrowGameRuntimeExceptionWhenSymbolIdIsInvalid() {
        assertThrows(GameRuntimeException.class, () -> Symbol.getSymbolById(0));
        assertThrows(GameRuntimeException.class, () -> Symbol.getSymbolById(4));
    }
}