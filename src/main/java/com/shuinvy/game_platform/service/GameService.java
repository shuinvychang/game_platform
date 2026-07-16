package com.shuinvy.game_platform.service;

import com.shuinvy.game_platform.dto.GameRequest;
import com.shuinvy.game_platform.dto.GameResponse;
import com.shuinvy.game_platform.model.Game;

import java.util.List;

public interface GameService {

    Game checkExists(Integer id);

    boolean checkUnique(String name, Integer id);

    GameResponse getById(Integer id);

    List<GameResponse> getList();

    Integer create(GameRequest request);

    void update(Integer id, GameRequest request);

    void delete(Integer id);
}
