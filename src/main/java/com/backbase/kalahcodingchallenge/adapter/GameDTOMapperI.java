package com.backbase.kalahcodingchallenge.adapter;

import com.backbase.kalahcodingchallenge.model.GameDTO;
import com.backbase.kalahcodingchallenge.model.GameModel;
import org.springframework.stereotype.Service;

public interface GameDTOMapperI {

    public GameDTO map(GameModel gameModel);
}
