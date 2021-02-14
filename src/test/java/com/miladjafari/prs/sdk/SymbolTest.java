package com.miladjafari.prs.sdk;

import com.miladjafari.prs.sdk.exception.GameRuntimeException;
import org.junit.jupiter.api.Test;

import static com.miladjafari.prs.sdk.Symbol.PAPER;
import static com.miladjafari.prs.sdk.Symbol.ROCK;
import static com.miladjafari.prs.sdk.Symbol.SCISSORS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SymbolTest {

    @Test
    public void testPaperShouldBeatsRockAndShouldNotBeatsScissors() {
        assertTrue(PAPER.beats(ROCK));
        assertFalse(PAPER.beats(SCISSORS));
    }

    @Test
    public void testRockShouldBeatsScissorsAndShouldNotBeatsPaper() {
        assertTrue(ROCK.beats(SCISSORS));
        assertFalse(ROCK.beats(PAPER));
    }

    @Test
    public void testScissorsShouldBestsPaperAndShouldNotBeatsRock() {
        assertTrue(SCISSORS.beats(PAPER));
        assertFalse(SCISSORS.beats(ROCK));
    }

    @Test
    public void testSuccessfullyGetSymbolById() {
        assertEquals(PAPER, Symbol.getSymbolById(1));
        assertEquals(ROCK, Symbol.getSymbolById(2));
        assertEquals(SCISSORS, Symbol.getSymbolById(3));
    }

    @Test
    public void testThrowGameRuntimeExceptionWhenSymbolIdIsInvalid() {
        assertThrows(GameRuntimeException.class, () -> Symbol.getSymbolById(0));
        assertThrows(GameRuntimeException.class, () -> Symbol.getSymbolById(4));
    }
}