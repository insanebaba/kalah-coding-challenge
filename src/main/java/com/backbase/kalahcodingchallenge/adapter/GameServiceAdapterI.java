package com.backbase.kalahcodingchallenge.adapter;

import com.backbase.kalahcodingchallenge.exception.GameDoesNotExistException;
import com.backbase.kalahcodingchallenge.exception.InvalidMoveException;
import com.backbase.kalahcodingchallenge.exception.NoMoreMovesPossibleException;
import com.backbase.kalahcodingchallenge.models.dto.GameDTO;

public interface GameServiceAdapterI {
    GameDTO createNewGame();

    GameDTO makeMove(int gameId, int pitId) throws GameDoesNotExistException, NoMoreMovesPossibleException, InvalidMoveException;
}
