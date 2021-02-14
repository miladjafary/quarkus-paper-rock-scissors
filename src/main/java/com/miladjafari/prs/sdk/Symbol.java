package com.miladjafari.prs.sdk;

import com.miladjafari.prs.sdk.exception.GameRuntimeException;

import java.util.Arrays;

public enum Symbol {
    PAPER(1) {
        @Override
        public Boolean beats(Symbol otherSymbol) {
            return otherSymbol.equals(ROCK);
        }
    },
    ROCK(2) {
        @Override
        public Boolean beats(Symbol otherSymbol) {
            return otherSymbol.equals(SCISSORS);
        }
    },
    SCISSORS(3) {
        @Override
        public Boolean beats(Symbol otherSymbol) {
            return otherSymbol.equals(PAPER);
        }
    };

    private final Integer id;

    Symbol(Integer id) {
        this.id = id;
    }

    public static Symbol getSymbolById(Integer id) {
        return Arrays.stream(values())
                     .filter(symbol -> symbol.id.equals(id))
                     .findFirst()
                     .orElseThrow(() -> new GameRuntimeException("Could find symbol with id: [%s]", id));
    }

    public abstract Boolean beats(Symbol otherSymbol);
}
