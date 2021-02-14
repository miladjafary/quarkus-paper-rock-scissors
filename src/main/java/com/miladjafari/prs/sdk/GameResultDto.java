package com.miladjafari.prs.sdk;

import com.miladjafari.prs.sdk.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameResultDto {
    private final List<Player> players = new ArrayList<>();

    private String gameId = "";
    private String winnerPlayerId = "";
    private Boolean isEqual = false;

    private GameResultDto() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getGameId() {
        return gameId;
    }

    public String getWinnerPlayerId() {
        return winnerPlayerId;
    }

    public Boolean isEqual() {
        return isEqual;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public static class Builder {
        private final GameResultDto instance = new GameResultDto();

        public Builder gameId(String gameId) {
            instance.gameId = gameId;
            return this;
        }

        public Builder addPlayer(Player player) {
            instance.players.add(player);
            return this;
        }

        public Builder winner(Player player) {
            instance.isEqual = false;
            instance.winnerPlayerId = player.getId();
            return this;
        }

        public Builder equalSymbol() {
            instance.isEqual = true;
            instance.winnerPlayerId = "";
            return this;
        }

        public GameResultDto build() {
            return instance;
        }
    }
}
