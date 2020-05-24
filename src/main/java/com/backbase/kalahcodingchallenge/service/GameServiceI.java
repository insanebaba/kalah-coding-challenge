package com.backbase.kalahcodingchallenge.service;

import com.backbase.kalahcodingchallenge.exception.GameDoesNotExistException;
import com.backbase.kalahcodingchallenge.exception.InvalidMoveException;
import com.backbase.kalahcodingchallenge.exception.NoMoreMovesPossibleException;
import com.backbase.kalahcodingchallenge.model.GameModel;

public interface GameServiceI {
    public GameModel createNewGame();

    public GameModel makeMove(int gameId, int pitId) throws GameDoesNotExistException, NoMoreMovesPossibleException, InvalidMoveException;
}