package com.shuinvy.game_platform.dao;

import com.shuinvy.game_platform.dto.RolePermissionRequest;
import com.shuinvy.game_platform.model.RolePermission;

import java.util.List;

public interface RolePermitDao {

    RolePermission getById(Integer id);

    RolePermission getByRolePermitId(Integer roleId, Integer permitId);

    List<RolePermission> getList();

    List<RolePermission> getListByRoleId(Integer roleId);

    Integer create(RolePermissionRequest request);

    void update(Integer id, RolePermissionRequest request);

    void delete(Integer id);
}
