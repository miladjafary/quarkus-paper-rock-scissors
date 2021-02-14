package com.miladjafari.prs.service;

import com.miladjafari.prs.sdk.dto.CreateGameResponseDto;
import com.miladjafari.prs.sdk.dto.GameResultDto;
import com.miladjafari.prs.sdk.engine.GameEngine;
import com.miladjafari.prs.sdk.engine.SinglePlayerGameEngineBuilder;
import com.miladjafari.prs.sdk.exception.GameRuntimeException;
import com.miladjafari.prs.sdk.player.Symbol;
import com.miladjafari.prs.repository.GameEngineRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class GameManagerService {
    private static final Logger logger = Logger.getLogger(GameManagerService.class.getName());

    @Inject
    GameEngineRepository gameEngineRepository;

    public CreateGameResponseDto createSinglePlayerGame() {
        String gameId = UUID.randomUUID().toString();
        String playerId = UUID.randomUUID().toString();

        GameEngine gameEngine = new SinglePlayerGameEngineBuilder()
                .gameId(gameId)
                .playerId(playerId)
                .build();

        gameEngineRepository.add(gameId, gameEngine);

        logger.info(String.format("New game has been created with id [%s]", gameId));

        return new CreateGameResponseDto(gameId, playerId);
    }

    public GameResultDto play(String gameId, String playerId, Integer symbolId) {
        GameEngine gameEngine = findById(gameId);
        Symbol selectedSymbol = Symbol.getSymbolById(symbolId);

        GameResultDto gameResultDto = gameEngine.play(playerId, selectedSymbol);

        return gameResultDto;
    }

    private GameEngine findById(String gameId) {
        return gameEngineRepository.findById(gameId)
                                   .orElseThrow(
                                           () -> new GameRuntimeException("Could not found game with id:[%s]", gameId));
    }
}
