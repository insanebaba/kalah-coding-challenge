package com.backbase.kalahcodingchallenge.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createNewGame() throws Exception {
        throw new Exception("Method createNewGame not implemented");
    }

    @PutMapping(value = "/{gameId}/pits/{pitId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> makeMove(@PathVariable("gameId") final int gameId,
            @PathVariable("pitId") final int pitId) throws Exception {
        throw new Exception("Method makeMove not implemented");
    }

}