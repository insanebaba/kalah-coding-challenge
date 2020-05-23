package com.backbase.kalahcodingchallenge.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collections;

@Entity
@Table(name = "games")
public record GameModel(@Id
                        @GeneratedValue Integer gameId,
                        @Type(type = "int-array")
                        @Column(name = "pits", columnDefinition = "int[]")
                        Integer[]pits,
                        @Enumerated(EnumType.STRING)GameCurrentStatus status) {

    public GameModel() {
        this(null, Collections.nCopies(GameConfig.pitsSize, GameConfig.seedsCount).toArray(new Integer[GameConfig.pitsSize])
                , GameCurrentStatus.STARTED);
    }
}