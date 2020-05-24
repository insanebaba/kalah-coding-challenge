package com.backbase.kalahcodingchallenge.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collections;

@Entity
@Table(name = "games")
public record GameModel(@Id
                        @GeneratedValue Integer gameId,
                        @Lob Integer[]pits,
                        @Enumerated(EnumType.STRING)GameCurrentStatus status) {

    public GameModel() {
        this(null, Collections.nCopies(GameConfig.PITS_SIZE, GameConfig.SEEDS_COUNT).toArray(new Integer[GameConfig.PITS_SIZE])
                , GameCurrentStatus.STARTED);
    }

    public GameModel(GameModel model, GameCurrentStatus status) {
        this(model.gameId, model.pits
                , status);
    }


}