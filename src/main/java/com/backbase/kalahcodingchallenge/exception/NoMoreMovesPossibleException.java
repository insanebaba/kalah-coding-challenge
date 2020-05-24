package com.backbase.kalahcodingchallenge.exception;

public class NoMoreMovesPossibleException extends Throwable {
    public NoMoreMovesPossibleException(int gameId, int pitId) {
        super("No More moves Possible as game with ID : " + gameId + " is finished");
    }
}
