package com.shuinvy.game_platform.dao;

import com.shuinvy.game_platform.dto.GameTypeRequest;
import com.shuinvy.game_platform.model.GameType;

import java.util.List;

public interface GameTypeDao {

    GameType getById(Integer id);

    List<GameType> getList();

    List<GameType> getListByIds(List<Integer> ids);

    Integer create(GameTypeRequest request);

    void update(Integer id, GameTypeRequest request);

    void delete(Integer id);
}
