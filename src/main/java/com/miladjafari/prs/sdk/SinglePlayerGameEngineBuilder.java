package com.miladjafari.prs.sdk;

import com.miladjafari.prs.sdk.exception.GameRuntimeException;
import com.miladjafari.prs.sdk.player.Player;

import java.util.UUID;

public class SinglePlayerGameEngineBuilder {

    public String gameId;
    public String realPlayerId;

    public SinglePlayerGameEngineBuilder gameId(String gameId) {
        this.gameId = gameId;
        return this;
    }

    public SinglePlayerGameEngineBuilder playerId(String id) {
        this.realPlayerId = id;
        return this;
    }

    public SinglePlayerGameEngine build() {
        if (this.realPlayerId == null || this.realPlayerId.isEmpty()) {
            throw new GameRuntimeException("RealPlayerId has not been set");
        }

        if (this.gameId == null) {
            throw new GameRuntimeException("GameId has not been set");
        }

        return new SinglePlayerGameEngine(
                gameId,
                new Player(realPlayerId),
                new Player(UUID.randomUUID().toString()),
                new RandomSymbolSelector()
        );
    }
}
