package com.shuinvy.game_platform.service;

import com.shuinvy.game_platform.model.TypeMapping;

import java.util.List;

public interface TypeMappingService {

    List<TypeMapping> getByGameId(Integer gameId);

    void update(Integer gameId, List<Integer> gameTypes);

    void deleteByIds(List<Integer> ids);
}
