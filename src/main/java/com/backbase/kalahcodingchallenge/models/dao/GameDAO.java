package com.backbase.kalahcodingchallenge.models.dao;

import com.backbase.kalahcodingchallenge.models.app.GameConfig;

import javax.persistence.*;
import java.util.Collections;

@Entity
@Table(name = "games")
public record GameDAO(@Id
                      @GeneratedValue Integer gameId,
                      @Lob Integer[]pits,
                      @Enumerated(EnumType.STRING)GameCurrentStatus status) {

    public static GameDAO newGame() {
        var arr = Collections.nCopies(GameConfig.PITS_SIZE, GameConfig.SEEDS_COUNT).toArray(new Integer[GameConfig.PITS_SIZE]);
        arr[GameConfig.PLAYER1_KALAH_ID] = 0;
        arr[GameConfig.PLAYER2_KALAH_ID] = 0;
        return new GameDAO(null, arr
                , GameCurrentStatus.STARTED);
    }

    public GameDAO(GameDAO model, GameCurrentStatus status) {
        this(model.gameId, model.pits
                , status);
    }


}