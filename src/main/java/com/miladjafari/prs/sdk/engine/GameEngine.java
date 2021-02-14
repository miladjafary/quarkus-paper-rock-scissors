package com.miladjafari.prs.sdk.engine;

import com.miladjafari.prs.sdk.dto.GameResultDto;
import com.miladjafari.prs.sdk.player.Symbol;

public interface GameEngine {
    GameResultDto play(String playerId, Symbol realPlayerSymbol);
}
