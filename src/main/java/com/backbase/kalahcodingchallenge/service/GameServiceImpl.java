package com.backbase.kalahcodingchallenge.service;

import com.backbase.kalahcodingchallenge.exception.GameDoesNotExistException;
import com.backbase.kalahcodingchallenge.exception.InvalidMoveException;
import com.backbase.kalahcodingchallenge.exception.NoMoreMovesPossibleException;
import com.backbase.kalahcodingchallenge.model.GameConfig;
import com.backbase.kalahcodingchallenge.model.GameCurrentStatus;
import com.backbase.kalahcodingchallenge.model.GameModel;
import com.backbase.kalahcodingchallenge.repository.GameCRUDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameServiceImpl implements GameServiceI {

    private GameCRUDRepository gameCRUDRepository;

    public GameServiceImpl(@Autowired GameCRUDRepository gameCRUDRepository) {
        this.gameCRUDRepository = gameCRUDRepository;
    }

    @Override
    public GameModel createNewGame() {
        return gameCRUDRepository.save(new GameModel());
    }

    @Override
    public GameModel makeMove(int gameId, int pitId) throws GameDoesNotExistException, NoMoreMovesPossibleException, InvalidMoveException {
        Optional<GameModel> optionalGame = gameCRUDRepository.findById(gameId);

        if (optionalGame.isEmpty()) throw new GameDoesNotExistException(gameId);

        GameModel gameModel = optionalGame.get();

        if (pitId > GameConfig.pitsSize || pitId < 1 || gameModel.pits()[pitId - 1] == 0 || !isValidPitForCurrentPlayerMove(gameModel, pitId)) {
            throw new InvalidMoveException(gameId, pitId);
        }
        for (int i = 1; i <= gameModel.pits()[pitId-1]; i++) {
            //TODO implement nextPit(gameModel,i)
        }
        return gameModel;
    }

    private boolean isValidPitForCurrentPlayerMove(GameModel gameModel, int pitId) throws NoMoreMovesPossibleException {
        return switch (gameModel.status()) {
            case STARTED, PLAYER1_SHOULD_MOVE_NOW -> pitId > 0 && pitId < (GameConfig.pitsSize / 2);
            case PLAYER2_SHOULD_MOVE_NOW -> pitId > (GameConfig.pitsSize / 2) && pitId <= GameConfig.pitsSize;
            case PLAYER1_WON, PLAYER2_WON -> throw new NoMoreMovesPossibleException(gameModel.gameId(), pitId);
        };
    }
}