package com.miladjafari.prs.sdk.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RandomSymbolSelectorTest {

    @Test
    public void shouldGenerateRandomSymbol() {
        assertNotNull(new RandomSymbolSelector().nextSymbol());
    }
}