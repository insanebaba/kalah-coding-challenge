package com.backbase.kalahcodingchallenge.mapper;

import com.backbase.kalahcodingchallenge.models.dao.GameDAO;
import com.backbase.kalahcodingchallenge.models.dto.GameDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class GameDTOMapperImpl implements GameDTOMapperI {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Override
    public GameDTO map(GameDAO gameDAO) {
        String url = createUrl(gameDAO);

        return new GameDTO(gameDAO.gameId(),
                url, null);
    }

    private String createUrl(GameDAO gameDAO) {
        var request = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        return request.substring(0, request.lastIndexOf("/")) + "/games/" + gameDAO.gameId();
    }

}
