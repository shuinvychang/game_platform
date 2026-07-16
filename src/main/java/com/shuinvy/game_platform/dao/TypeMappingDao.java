package com.shuinvy.game_platform.dao;

import com.shuinvy.game_platform.dto.TypeMappingRequest;
import com.shuinvy.game_platform.model.TypeMapping;

import java.util.List;

public interface TypeMappingDao {

    TypeMapping getById(Integer id);

    List<TypeMapping> getByGameId(Integer gameId);

    /**
     * It will get data by game_id without filter the status
     **/
    List<TypeMapping> getByGameAll(Integer gameId);

    List<TypeMapping> getList();

    Integer create(TypeMappingRequest request);

    void createTypeIdsByGameId(List<Integer> typeIds, Integer gameId);

    void update(Integer id, TypeMappingRequest request);

    void updateStatusByIds(List<Integer> ids, Integer status);

    void delete(Integer id);

    void deleteByIds(List<Integer> ids);
}
