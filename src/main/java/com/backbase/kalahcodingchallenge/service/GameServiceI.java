package com.backbase.kalahcodingchallenge.service;

import com.backbase.kalahcodingchallenge.exception.GameDoesNotExistException;
import com.backbase.kalahcodingchallenge.exception.InvalidMoveException;
import com.backbase.kalahcodingchallenge.exception.NoMoreMovesPossibleException;
import com.backbase.kalahcodingchallenge.models.dao.GameDAO;

public interface GameServiceI {
    GameDAO createNewGame();

    GameDAO makeMove(int gameId, int pitId) throws GameDoesNotExistException, NoMoreMovesPossibleException, InvalidMoveException;
}