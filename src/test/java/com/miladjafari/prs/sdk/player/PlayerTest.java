package com.miladjafari.prs.sdk.player;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PlayerTest {

    @Test
    public void shouldCreatePlayer() {
        String id = UUID.randomUUID().toString();
        Player player = new Player(id);

        assertEquals(id, player.getId());
        assertEquals(0, player.getScore());
        assertNull(player.getSymbol());
    }

    @Test
    public void shouldIncreaseScore() {
        String id = UUID.randomUUID().toString();
        Player player = new Player(id);

        assertEquals(id, player.getId());
        assertEquals(0, player.getScore());

        player.increaseScore();
        assertEquals(1, player.getScore());
    }

    @Test
    public void shouldReturnSymbol() {
        String id = UUID.randomUUID().toString();
        Player player = new Player(id);

        player.setSymbol(Symbol.PAPER);
        assertEquals(Symbol.PAPER, player.getSymbol());
    }

    @Test
    public void shouldBeEqualsWhenEveryFieldsAreTheSame() {
        String id = UUID.randomUUID().toString();
        Player player1 = new Player(id);
        player1.setSymbol(Symbol.PAPER);

        Player player2 = new Player(id);
        player2.setSymbol(Symbol.PAPER);

        assertEquals(player1, player2);
        assertEquals(player1.hashCode(), player2.hashCode());
    }
}