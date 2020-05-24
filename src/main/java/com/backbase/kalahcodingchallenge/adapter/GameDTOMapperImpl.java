package com.backbase.kalahcodingchallenge.adapter;

import com.backbase.kalahcodingchallenge.model.GameDTO;
import com.backbase.kalahcodingchallenge.model.GameModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.backbase.kalahcodingchallenge.controller.GameController.CONTROLLER_PATH;

@Component
//@Slf4j
public class GameDTOMapperImpl implements GameDTOMapperI {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Override
    public GameDTO map(GameModel gameModel) {
        String url = createUrl(gameModel);

        return new GameDTO(gameModel.gameId(),
                url, null);
    }

    private String createUrl(GameModel gameModel) {
        var request = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        return request.substring(0, request.lastIndexOf("/")) + "/games/" + gameModel.gameId();
    }

}
