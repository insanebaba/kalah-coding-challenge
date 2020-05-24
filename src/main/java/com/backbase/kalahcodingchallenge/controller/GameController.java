package com.backbase.kalahcodingchallenge.controller;

import com.backbase.kalahcodingchallenge.adapter.GameServiceAdapterI;
import com.backbase.kalahcodingchallenge.exception.NoMoreMovesPossibleException;
import com.backbase.kalahcodingchallenge.model.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(GameController.CONTROLLER_PATH)
public class GameController {

    private GameServiceAdapterI gameServiceAdapter;

    public static final String CONTROLLER_PATH = "games";

    public GameController(@Autowired GameServiceAdapterI gameServiceAdapter) {
        this.gameServiceAdapter = gameServiceAdapter;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDTO createNewGame() throws Exception {
        return gameServiceAdapter.createNewGame();
    }

    @PutMapping("/{gameId}/pits/{pitId}")
    @ResponseStatus(HttpStatus.OK)
    public GameDTO makeMove(@PathVariable("gameId") final int gameId,
                            @PathVariable("pitId") final int pitId
    ) throws Exception, NoMoreMovesPossibleException {
        return gameServiceAdapter.makeMove(gameId, pitId);
    }

}