package com.backbase.kalahcodingchallenge.service;

import com.backbase.kalahcodingchallenge.exception.GameDoesNotExistException;
import com.backbase.kalahcodingchallenge.exception.InvalidMoveException;
import com.backbase.kalahcodingchallenge.exception.NoMoreMovesPossibleException;
import com.backbase.kalahcodingchallenge.models.app.GameConfig;
import com.backbase.kalahcodingchallenge.models.dao.GameCurrentStatus;
import com.backbase.kalahcodingchallenge.models.dao.GameDAO;
import com.backbase.kalahcodingchallenge.repository.GameCRUDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.backbase.kalahcodingchallenge.models.app.GameConfig.*;
import static com.backbase.kalahcodingchallenge.models.dao.GameCurrentStatus.PLAYER1_SHOULD_MOVE_NOW;
import static com.backbase.kalahcodingchallenge.models.dao.GameCurrentStatus.PLAYER2_SHOULD_MOVE_NOW;

@Service
public class GameServiceImpl implements GameServiceI {

    private GameCRUDRepository gameCRUDRepository;

    public GameServiceImpl(@Autowired GameCRUDRepository gameCRUDRepository) {
        this.gameCRUDRepository = gameCRUDRepository;
    }

    @Override
    public GameDAO createNewGame() {
        return gameCRUDRepository.save(GameDAO.newGame());
    }

    @Override
    public GameDAO makeMove(final int gameId, final int pitId) throws GameDoesNotExistException, NoMoreMovesPossibleException, InvalidMoveException {
        Optional<GameDAO> optionalGame = gameCRUDRepository.findById(gameId);

        if (optionalGame.isEmpty()) throw new GameDoesNotExistException(gameId);

        var gameModel = optionalGame.get();
        // pit number to index
        var workingPitId = pitId - 1;

        //check if the pitID is valid else throw exception
        if (pitId > PITS_SIZE || pitId < 1 || pitId % 7 == 0 || gameModel.pits()[workingPitId] == 0 || !isValidPitForCurrentPlayerMove(gameModel, workingPitId)) {
            throw new InvalidMoveException(gameId, pitId);
        }

        //Check if this is when the game started, either of the players can make first move
        if (gameModel.status() == GameCurrentStatus.STARTED) {
            if (GameConfig.PLAYER1_PITS_IDS.contains(workingPitId)) {
                gameModel = new GameDAO(gameModel, PLAYER1_SHOULD_MOVE_NOW);
            } else {
                gameModel = new GameDAO(gameModel, PLAYER2_SHOULD_MOVE_NOW);
            }
            //persist in repository
            gameCRUDRepository.save(gameModel);
        }
        //How long should the loop run 1..currentStoneCount
        var maxLoop = gameModel.pits()[workingPitId];

        //Set the current pit stone count to zero
        gameModel.pits()[workingPitId] = 0;
        // The Kalah to skip
        int skippableKalahId = PLAYER2_SHOULD_MOVE_NOW.equals(gameModel.status()) ? PLAYER1_KALAH_ID : PLAYER2_KALAH_ID;
        //Find First Pit to increment stone count
        var nextPitId = findNextPitId(workingPitId, skippableKalahId);
        //Sequentially update the pits stones
        while (maxLoop > 0) {
            gameModel.pits()[nextPitId]++;
            //check if this is the last pit move
            if (maxLoop == 1) {
                //should take all pits balls and add it to Players kalah, If this last moved pit has only one stone and belongs to player pit
                if (gameModel.pits()[nextPitId] == 1) {
                    switch (gameModel.status()) {
                        case PLAYER1_SHOULD_MOVE_NOW -> {
                            if (GameConfig.PLAYER1_PITS_IDS.contains(nextPitId)) {
                                gameModel.pits()[nextPitId] = 0;
                                gameModel.pits()[PLAYER1_KALAH_ID]++;
                                // Take all the stones of opposite pit and  put them to current player Kalah
                                Integer stonesInOppositePit = gameModel.pits()[GameConfig.PLAYER1_PITS_MAP.get(nextPitId)];
                                gameModel.pits()[GameConfig.PLAYER1_PITS_MAP.get(nextPitId)] = 0;
                                gameModel.pits()[PLAYER1_KALAH_ID] += stonesInOppositePit;
                            }
                        }
                        case PLAYER2_SHOULD_MOVE_NOW -> {
                            if (GameConfig.PLAYER2_PITS_IDS.contains(nextPitId)) {
                                gameModel.pits()[nextPitId] = 0;
                                gameModel.pits()[PLAYER2_KALAH_ID]++;
                                // Take all the stones of opposite pit and  put them to current player Kalah
                                Integer stonesInOppositePit = gameModel.pits()[GameConfig.PLAYER2_PITS_MAP.get(nextPitId)];
                                gameModel.pits()[GameConfig.PLAYER2_PITS_MAP.get(nextPitId)] = 0;
                                gameModel.pits()[PLAYER2_KALAH_ID] += stonesInOppositePit;
                            }
                        }
                    }
                }
                // Are All My pits empty -> take all opposite pits stones to my kalah
                if (areAllMyPitsEmpty(gameModel)) {
                    moveAllStonesOfOppositePlayerToCurrentPlayerKalah(gameModel);
                    gameModel = new GameDAO(gameModel, GameCurrentStatus.FINISHED);
                } else

                    // Are All opposite pits empty -> take all my stones to opposite kalah
                    if (areAllPlayerPitsEmptyForOppositePlayer(gameModel)) {
                    moveAllStonesOfCurrentPlayerToOppositePlayerKalah(gameModel,pitId);
                    gameModel = new GameDAO(gameModel, GameCurrentStatus.FINISHED);
                } else
                    //Update game status if not landed in my own Kalah
                    //If ended in my kalah , then I get another turn
                    if (PLAYER1_SHOULD_MOVE_NOW.equals(gameModel.status()) && !PLAYER1_KALAH_ID.equals(nextPitId)) {
                    gameModel = new GameDAO(gameModel, PLAYER2_SHOULD_MOVE_NOW);
                } else if (PLAYER2_SHOULD_MOVE_NOW.equals(gameModel.status()) && !PLAYER2_KALAH_ID.equals(nextPitId)) {
                    gameModel = new GameDAO(gameModel, PLAYER1_SHOULD_MOVE_NOW);
                }
            }
            nextPitId = findNextPitId(nextPitId, skippableKalahId);
            maxLoop--;
        }
        gameCRUDRepository.save(gameModel);
        return gameModel;
    }

    private void moveAllStonesOfCurrentPlayerToOppositePlayerKalah(GameDAO gameDAO,int pitId) throws InvalidMoveException {
        switch (gameDAO.status()) {
            case PLAYER1_SHOULD_MOVE_NOW -> GameConfig.PLAYER1_PITS_IDS.forEach(pitIndex -> {
                gameDAO.pits()[PLAYER2_KALAH_ID] += gameDAO.pits()[pitIndex];
                gameDAO.pits()[pitIndex] = 0;
            });
            case PLAYER2_SHOULD_MOVE_NOW -> GameConfig.PLAYER2_PITS_IDS.forEach(pitIndex -> {
                gameDAO.pits()[PLAYER1_KALAH_ID] += gameDAO.pits()[pitIndex];
                gameDAO.pits()[pitIndex] = 0;
            });
            default -> throw new InvalidMoveException(gameDAO.gameId(),pitId);
        }
    }

    private boolean areAllPlayerPitsEmptyForOppositePlayer(GameDAO gameDAO) {
        return switch (gameDAO.status()) {
            case PLAYER1_SHOULD_MOVE_NOW -> GameConfig.PLAYER2_PITS_IDS.stream().allMatch(pitIndex -> gameDAO.pits()[pitIndex] == 0);
            case PLAYER2_SHOULD_MOVE_NOW -> GameConfig.PLAYER1_PITS_IDS.stream().allMatch(pitIndex -> gameDAO.pits()[pitIndex] == 0);
            case FINISHED, STARTED -> false;
        };
    }

    private void moveAllStonesOfOppositePlayerToCurrentPlayerKalah(GameDAO gameDAO) {
        switch (gameDAO.status()) {
            case PLAYER1_SHOULD_MOVE_NOW -> GameConfig.PLAYER2_PITS_IDS.forEach(pitIndex -> {
                gameDAO.pits()[PLAYER1_KALAH_ID] += gameDAO.pits()[pitIndex];
                gameDAO.pits()[pitIndex] = 0;
            });
            case PLAYER2_SHOULD_MOVE_NOW -> GameConfig.PLAYER1_PITS_IDS.forEach(pitIndex -> {
                gameDAO.pits()[PLAYER2_KALAH_ID] += gameDAO.pits()[pitIndex];
                gameDAO.pits()[pitIndex] = 0;
            });
            default -> throw new IllegalStateException("Unexpected value: " + gameDAO.status());
        }
    }

    /**
     * Check if the current Player index are empty
     *
     * @param gameDAO Model to test for
     * @return if all the pits of current player are empty
     */
    private boolean areAllMyPitsEmpty(GameDAO gameDAO) {
        return switch (gameDAO.status()) {
            case PLAYER1_SHOULD_MOVE_NOW -> GameConfig.PLAYER1_PITS_IDS.stream().allMatch(pitIndex -> gameDAO.pits()[pitIndex] == 0);
            case PLAYER2_SHOULD_MOVE_NOW -> GameConfig.PLAYER2_PITS_IDS.stream().allMatch(pitIndex -> gameDAO.pits()[pitIndex] == 0);
            case FINISHED, STARTED -> false;
        };
    }

    private Integer findNextPitId(Integer workingPitId, Integer skippableKalahId) {
        if (workingPitId != (PITS_SIZE / 2) - 2 && workingPitId < (PITS_SIZE - 2))
            return workingPitId + 1;
        else if (workingPitId + 1 == skippableKalahId && workingPitId < PITS_SIZE / 2)
            return workingPitId + 2;
        return 0;
    }

    private boolean isValidPitForCurrentPlayerMove(GameDAO gameDAO, int workingPitId) throws NoMoreMovesPossibleException {
        return switch (gameDAO.status()) {
            case STARTED -> GameConfig.PLAYER1_PITS_IDS.contains(workingPitId) || GameConfig.PLAYER2_PITS_IDS.contains(workingPitId);
            case PLAYER1_SHOULD_MOVE_NOW -> GameConfig.PLAYER1_PITS_IDS.contains(workingPitId);
            case PLAYER2_SHOULD_MOVE_NOW -> GameConfig.PLAYER2_PITS_IDS.contains(workingPitId);
            case FINISHED -> throw new NoMoreMovesPossibleException(gameDAO.gameId(), workingPitId);
        };
    }
}