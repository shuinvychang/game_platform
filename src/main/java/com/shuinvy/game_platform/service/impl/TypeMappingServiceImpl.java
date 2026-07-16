package com.shuinvy.game_platform.service.impl;

import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.dao.TypeMappingDao;
import com.shuinvy.game_platform.model.TypeMapping;
import com.shuinvy.game_platform.service.TypeMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TypeMappingServiceImpl implements TypeMappingService {

    @Autowired
    private TypeMappingDao typeMappingDao;

    @Override
    public List<TypeMapping> getByGameId(Integer gameId) {
        return typeMappingDao.getByGameId(gameId);
    }

    @Override
    public void update(Integer gameId, List<Integer> gameTypes) {
        List<Integer> deletedList = new ArrayList<>();
        List<Integer> existList = new ArrayList<>();
        List<Integer> updateList = new ArrayList<>();
        List<Integer> addedList = new ArrayList<>();
        // Current types of the game
        List<TypeMapping> typeMappings = typeMappingDao.getByGameAll(gameId);
        for (TypeMapping typeMapping : typeMappings) {
            if (!gameTypes.contains(typeMapping.getTypeId())) {
                // Current type is not in gameTypes,
                // so we need to delete it
                deletedList.add(typeMapping.getId());
                continue;
            }
            if (typeMapping.getStatus().equals(Status.DELETED)) {
                // The type we set is deleted,
                // so we need to update its status to enabled
                updateList.add(typeMapping.getId());
            } else {
                existList.add(typeMapping.getId());
            }
        }
        for (Integer typeId : gameTypes) {
            if (existList.contains(typeId)) {
                continue;
            }
            if (updateList.contains(typeId)) {
                continue;
            }
            // The type doesn't exist in mapping,
            // so we need to add it
            addedList.add(typeId);
        }
        if (!deletedList.isEmpty()) {
            typeMappingDao.deleteByIds(deletedList);
        }
        if (!updateList.isEmpty()) {
            typeMappingDao.updateStatusByIds(updateList, Status.ENABLED);
        }
        if (!addedList.isEmpty()) {
            typeMappingDao.createTypeIdsByGameId(addedList, gameId);
        }
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        typeMappingDao.deleteByIds(ids);
    }
}
