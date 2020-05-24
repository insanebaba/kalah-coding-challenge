package com.backbase.kalahcodingchallenge.adapter;

import com.backbase.kalahcodingchallenge.exception.GameDoesNotExistException;
import com.backbase.kalahcodingchallenge.exception.InvalidMoveException;
import com.backbase.kalahcodingchallenge.exception.NoMoreMovesPossibleException;
import com.backbase.kalahcodingchallenge.model.GameDTO;
import com.backbase.kalahcodingchallenge.model.GameModel;

public interface GameServiceAdapterI {
    public GameDTO createNewGame();

    public GameDTO makeMove(int gameId, int pitId) throws GameDoesNotExistException, NoMoreMovesPossibleException, InvalidMoveException;
}
