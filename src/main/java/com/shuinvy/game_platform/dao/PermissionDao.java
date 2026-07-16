package com.shuinvy.game_platform.dao;

import com.shuinvy.game_platform.dto.PermissionRequest;
import com.shuinvy.game_platform.model.Permission;

import java.util.List;

public interface PermissionDao {

    Permission getById(Integer id);

    Permission getByPageButton(String page, String button);

    List<Permission> getList();

    Integer create(PermissionRequest request);

    void update(Integer id, PermissionRequest request);

    void delete(Integer id);
}
