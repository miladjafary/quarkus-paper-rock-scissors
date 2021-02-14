package com.miladjafari.prs.sdk;

import java.util.Random;

public class RandomSymbolSelector {

    private final Random random = new Random();

    public Symbol nextSymbol() {
        int symbolId = random.nextInt(3) + 1;
        return Symbol.getSymbolById(symbolId);
    }
}
