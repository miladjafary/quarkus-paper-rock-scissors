package com.miladjafari.prs.sdk.exception;

public class GameRuntimeException extends  RuntimeException {
    public GameRuntimeException(String message, Object... args) {
        super(String.format(message, args));
    }
}
