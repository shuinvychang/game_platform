package com.shuinvy.game_platform.service;

import com.shuinvy.game_platform.dto.PermissionRequest;
import com.shuinvy.game_platform.model.Permission;

import java.util.List;

public interface PermissionService {

    Permission getById(Integer id);

    boolean checkExists(String page, String button, Integer id);

    List<Permission> getList();

    Integer create(PermissionRequest request);

    void update(Integer id, PermissionRequest request);

    void delete(Integer id);
}
