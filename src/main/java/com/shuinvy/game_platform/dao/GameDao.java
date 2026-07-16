package com.shuinvy.game_platform.dao;

import com.shuinvy.game_platform.dto.GameRequest;
import com.shuinvy.game_platform.model.Game;

import java.util.List;

public interface GameDao {

    Game getById(Integer id);

    Game getByName(String name);

    List<Game> getList();

    Integer create(GameRequest request);

    void update(Integer id, GameRequest request);

    void delete(Integer id);
}
