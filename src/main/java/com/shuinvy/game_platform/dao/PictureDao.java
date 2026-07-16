package com.shuinvy.game_platform.dao;

import com.shuinvy.game_platform.dto.PictureRequest;
import com.shuinvy.game_platform.model.Picture;

import java.util.List;

public interface PictureDao {

    Picture getById(Integer id);

    List<Picture> getList();

    List<Picture> getListByIds(List<Integer> ids);

    List<Picture> getListByGameId(Integer gameId);

    Integer create(PictureRequest request);

    void update(Integer id, PictureRequest request);

    void updateIdsByGameId(List<Integer> ids, Integer gameId);

    void delete(Integer id);

    void deleteByIds(List<Integer> ids);
}
