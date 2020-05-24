package com.backbase.kalahcodingchallenge.adapter;

import com.backbase.kalahcodingchallenge.exception.GameDoesNotExistException;
import com.backbase.kalahcodingchallenge.exception.InvalidMoveException;
import com.backbase.kalahcodingchallenge.exception.NoMoreMovesPossibleException;
import com.backbase.kalahcodingchallenge.mapper.GameDTOMapperI;
import com.backbase.kalahcodingchallenge.models.dto.GameDTO;
import com.backbase.kalahcodingchallenge.service.GameServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceAdapterImpl implements GameServiceAdapterI {
    private GameDTOMapperI gameDTOMapperI;
    private GameServiceI gameService;

    private String baseUrl;

    public GameServiceAdapterImpl(@Autowired final GameDTOMapperI gameDTOMapperI,
                                  @Autowired final GameServiceI gameService
    ) {
        this.gameDTOMapperI = gameDTOMapperI;
        this.gameService = gameService;
    }

    @Override
    public GameDTO createNewGame() {
        return gameDTOMapperI.map(gameService.createNewGame());
    }

    @Override
    public GameDTO makeMove(int gameId, int pitId) throws GameDoesNotExistException, NoMoreMovesPossibleException, InvalidMoveException {
        return gameDTOMapperI.map(gameService.makeMove(gameId, pitId));
    }
}
