package com.miladjafari.prs.sdk.player;

import com.miladjafari.prs.sdk.Symbol;

import java.util.Objects;

public class Player {

    private final String id;
    private Symbol symbol;
    private Integer score = 0;

    public Player(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Integer getScore() {
        return score;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public void increaseScore() {
        ++score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && symbol == player.symbol && Objects.equals(score,
                                                                                          player.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, symbol, score);
    }
}

