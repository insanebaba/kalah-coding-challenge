package com.backbase.kalahcodingchallenge.exception;

public class GameDoesNotExistException extends Exception {
    public GameDoesNotExistException(int gameId) {
        super("Game with id : " + gameId + " doesn't exist");
    }
}
