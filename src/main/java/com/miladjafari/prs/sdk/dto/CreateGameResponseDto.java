package com.miladjafari.prs.sdk.dto;

public class CreateGameResponseDto {
    private String gameId;
    private String playerId;
    private String url;

    public CreateGameResponseDto() {}

    public CreateGameResponseDto(String gameId, String playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.url = String.format("/games/%s", gameId);
    }

    public String getGameId() {
        return gameId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getUrl() {
        return url;
    }
}
