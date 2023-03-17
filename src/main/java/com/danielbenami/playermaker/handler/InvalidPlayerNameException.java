package com.danielbenami.playermaker.handler;

public class InvalidPlayerNameException extends RuntimeException{

    public InvalidPlayerNameException(String message) {
        super(message);
    }
}
