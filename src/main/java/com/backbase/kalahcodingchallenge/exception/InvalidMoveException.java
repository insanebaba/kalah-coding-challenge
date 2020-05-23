package com.backbase.kalahcodingchallenge.exception;

public class InvalidMoveException extends Exception {
    public InvalidMoveException(int gameId, int pitId) {
        super("Move on pit:"+pitId+" in game:"+gameId+" is not possible");
    }
}
