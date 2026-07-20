package com.shuinvy.game_platform.service.impl;

import com.shuinvy.game_platform.dao.PermissionDao;
import com.shuinvy.game_platform.dto.PermissionRequest;
import com.shuinvy.game_platform.model.Permission;
import com.shuinvy.game_platform.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Permission getById(Integer id) {
        return permissionDao.getById(id);
    }

    @Override
    public boolean checkExists(String page, String button, Integer id) {
        Permission obj = permissionDao.getByPageButton(page,button);
        if (obj == null) {
            return false;
        }
        if (id == 0) {
            return true;
        }
        return obj.getId().equals(id);
    }

    @Override
    public List<Permission> getList() {
        return permissionDao.getList();
    }

    @Override
    public Integer create(PermissionRequest request) {
        return permissionDao.create(request);
    }

    @Override
    public void update(Integer id, PermissionRequest request) {
        Permission obj = permissionDao.getById(id);
        if (request.getPage() == null) {
            request.setPage(obj.getPage());
        }
        if (request.getButton() == null) {
            request.setButton(obj.getButton());
        }
        if (request.getStatus() == null) {
            request.setStatus(obj.getStatus());
        }
        permissionDao.update(id, request);
    }

    @Override
    public void delete(Integer id) {
        permissionDao.delete(id);
    }
}
