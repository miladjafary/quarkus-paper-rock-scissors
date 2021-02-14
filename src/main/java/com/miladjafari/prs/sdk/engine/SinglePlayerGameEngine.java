package com.miladjafari.prs.sdk.engine;

import com.miladjafari.prs.sdk.dto.GameResultDto;
import com.miladjafari.prs.sdk.exception.GameRuntimeException;
import com.miladjafari.prs.sdk.player.Player;
import com.miladjafari.prs.sdk.player.Symbol;

public class SinglePlayerGameEngine implements GameEngine {

    private final String gameId;
    private final Player realPlayer;
    private final Player computerPlayer;
    private final RandomSymbolSelector computerPlayerSymbolSelector;

    public SinglePlayerGameEngine(
            String gameId,
            Player realPlayer,
            Player computerPlayer,
            RandomSymbolSelector computerPlayerSymbolSelector
    ) {
        this.gameId = gameId;
        this.realPlayer = realPlayer;
        this.computerPlayer = computerPlayer;
        this.computerPlayerSymbolSelector = computerPlayerSymbolSelector;
    }

    public GameResultDto play(String playerId, Symbol realPlayerSymbol) {
        playRandomSymbolForComputerPlayer();
        setPlayerSymbolOrThrowExceptionIfPlayerIsNotExist(playerId, realPlayerSymbol);

        GameResultDto.Builder gameResultBuilder = GameResultDto.builder()
                                                               .gameId(gameId)
                                                               .addPlayer(computerPlayer)
                                                               .addPlayer(realPlayer);

        if (haveEqualSymbols(realPlayer, computerPlayer)) {
            gameResultBuilder.equalSymbol();
        } else {
            Player winner = findWinner(realPlayer, computerPlayer);
            gameResultBuilder.winner(winner);
        }

        return gameResultBuilder.build();
    }

    private boolean haveEqualSymbols(Player realPlayer, Player computerPlayer) {
        return realPlayer.getSymbol().equals(computerPlayer.getSymbol());
    }

    private Player findWinner(Player realPlayer, Player computerPlayer) {
        Player winner;
        Symbol realPlayerSymbol = realPlayer.getSymbol();
        Symbol computerSymbol = computerPlayer.getSymbol();

        if (realPlayerSymbol.beats(computerSymbol)) {
            realPlayer.increaseScore();
            winner = realPlayer;
        } else {
            computerPlayer.increaseScore();
            winner = computerPlayer;
        }

        return winner;
    }

    private void playRandomSymbolForComputerPlayer() {
        computerPlayer.setSymbol(computerPlayerSymbolSelector.nextSymbol());
    }

    private void setPlayerSymbolOrThrowExceptionIfPlayerIsNotExist(String playerId, Symbol realPlayerSymbol) {
        if (!isRealPlayerExist(playerId)) {
            throw new GameRuntimeException("Could not found player with PlayerId [%s]", playerId);
        }

        realPlayer.setSymbol(realPlayerSymbol);
    }

    private Boolean isRealPlayerExist(String playerId) {
        return realPlayer.getId().equals(playerId);
    }
}
