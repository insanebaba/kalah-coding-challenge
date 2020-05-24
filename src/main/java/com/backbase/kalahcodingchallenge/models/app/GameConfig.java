package com.backbase.kalahcodingchallenge.models.app;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameConfig {
    public static final Integer PITS_SIZE = 14;
    public static final Integer SEEDS_COUNT = 6;
    public static final Integer PLAYER1_KALAH_ID = (GameConfig.PITS_SIZE / 2) - 1;
    public static final List<Integer> PLAYER1_PITS_IDS = IntStream.range(0, PLAYER1_KALAH_ID).boxed().collect(Collectors.toList());
    public static final Integer PLAYER2_KALAH_ID = GameConfig.PITS_SIZE - 1;
    public static final List<Integer> PLAYER2_PITS_IDS = IntStream.range(PLAYER1_KALAH_ID + 1, PLAYER2_KALAH_ID).boxed().collect(Collectors.toList());
    public static final Map<Integer, Integer> PLAYER1_PITS_MAP = PLAYER1_PITS_IDS.stream().collect(Collectors.toMap(
            i -> i,
            i -> ((PLAYER2_KALAH_ID - 1) - i)
    ));
    public static final Map<Integer, Integer> PLAYER2_PITS_MAP = PLAYER1_PITS_MAP.entrySet().stream().collect(
            Collectors.toMap(
                    Map.Entry::getValue,
                    Map.Entry::getKey));

}
