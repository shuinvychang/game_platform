package com.shuinvy.game_platform.service.impl;

import com.shuinvy.game_platform.dao.GameDao;
import com.shuinvy.game_platform.dao.GameTypeDao;
import com.shuinvy.game_platform.dao.PictureDao;
import com.shuinvy.game_platform.dao.TypeMappingDao;
import com.shuinvy.game_platform.dto.GameRequest;
import com.shuinvy.game_platform.dto.GameResponse;
import com.shuinvy.game_platform.dto.GameTypeResponse;
import com.shuinvy.game_platform.model.Game;
import com.shuinvy.game_platform.model.GameType;
import com.shuinvy.game_platform.model.TypeMapping;
import com.shuinvy.game_platform.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameDao gameDao;

    @Autowired
    private GameTypeDao gameTypeDao;

    @Autowired
    private TypeMappingDao typeMappingDao;

    @Autowired
    private PictureDao pictureDao;

    @Override
    public Game checkExists(Integer id) {
        return gameDao.getById(id);
    }

    @Override
    public boolean checkUnique(String name, Integer id) {
        Game game = gameDao.getByName(name);
        if (game == null) {
            return true;
        }
        if (id == 0) {
            return false;
        }
        return game.getId().equals(id);
    }

    @Override
    public GameResponse getById(Integer id) {
        Game game = gameDao.getById(id);
        return new GameResponse(
                game,
                getGameTypes(id),
                pictureDao.getListByGameId(id)
        );
    }

    @Override
    public List<GameResponse> getList() {
        List<Game> games = gameDao.getList();
        List<GameResponse> gameResponses = new ArrayList<>();
        for (Game game : games) {
            // Needless to put picture to list
            gameResponses.add(new GameResponse(
                    game,
                    getGameTypes(game.getId())
            ));
        }
        return gameResponses;
    }

    @Override
    public Integer create(GameRequest request) {
        return gameDao.create(request);
    }

    @Override
    public void update(Integer id, GameRequest request) {
        Game old = gameDao.getById(id);
        if (request.getName() == null) {
            request.setName(old.getName());
        }
        if (request.getInfo() == null) {
            request.setInfo(old.getInfo());
        }
        if (request.getDescription() == null) {
            request.setDescription(old.getDescription());
        }
        if (request.getPrice() == null) {
            request.setPrice(old.getPrice());
        }
        if (request.getIsPublished() == null) {
            request.setIsPublished(old.getIsPublished());
        }
        if (request.getPublished() == null) {
            request.setPublished(old.getPublished());
        }
        if (request.getStatus() == null) {
            request.setStatus(old.getStatus());
        }
        gameDao.update(id, request);
    }

    @Override
    public void delete(Integer id) {
        gameDao.delete(id);
    }

    private List<GameTypeResponse> getGameTypes(Integer gameId) {
        List<GameTypeResponse> result = new ArrayList<>();
        List<TypeMapping> typeMappings = typeMappingDao.getByGameId(gameId);
        List<Integer> typeIds = new ArrayList<>();
        for (TypeMapping typeMapping : typeMappings) {
            typeIds.add(typeMapping.getTypeId());
        }
        if (!typeIds.isEmpty()) {
            List<GameType> gameTypes = gameTypeDao.getListByIds(typeIds);
            for (GameType eachGameType : gameTypes) {
                result.add(new GameTypeResponse(
                        eachGameType.getId(),
                        eachGameType.getName(),
                        eachGameType.getCode()
                ));
            }
        }
        return result;
    }

}
