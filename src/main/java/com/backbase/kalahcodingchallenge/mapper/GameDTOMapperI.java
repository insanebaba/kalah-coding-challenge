package com.backbase.kalahcodingchallenge.mapper;

import com.backbase.kalahcodingchallenge.models.dto.GameDTO;
import com.backbase.kalahcodingchallenge.models.dao.GameDAO;

public interface GameDTOMapperI {

    GameDTO map(GameDAO gameDAO);
}
