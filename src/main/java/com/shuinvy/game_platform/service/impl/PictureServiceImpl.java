package com.shuinvy.game_platform.service.impl;

import com.shuinvy.game_platform.dao.PictureDao;
import com.shuinvy.game_platform.dto.PictureRequest;
import com.shuinvy.game_platform.model.Picture;
import com.shuinvy.game_platform.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    @Override
    public Picture getById(Integer id) {
        return pictureDao.getById(id);
    }

    @Override
    public List<Picture> getList() {
        return pictureDao.getList();
    }

    @Override
    public List<Picture> getListByIds(List<Integer> ids) {
        return pictureDao.getListByIds(ids);
    }

    @Override
    public Integer create(PictureRequest request) {
        return pictureDao.create(request);
    }

    @Override
    public void update(Integer id, PictureRequest request) {
        if (request.getStatus() == null) {
            request.setStatus(1);
        }
        pictureDao.update(id, request);
    }

    @Override
    public void updateIdsByGameId(List<Integer> ids, Integer gameId) {
        pictureDao.updateIdsByGameId(ids, gameId);
    }

    @Override
    public void delete(Integer id) {
        pictureDao.delete(id);
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        pictureDao.deleteByIds(ids);
    }
}
