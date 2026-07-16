package com.shuinvy.game_platform.service;

import com.shuinvy.game_platform.dto.RolePermissionRequest;
import com.shuinvy.game_platform.dto.RolePermitResponse;
import com.shuinvy.game_platform.model.RolePermission;

import java.util.List;

public interface RolePermitService {

    public RolePermitResponse getById(Integer id);

    public RolePermission getExists(Integer roleId, Integer permitId);

    public List<RolePermitResponse> getList();

    public List<RolePermitResponse> getListByRoleId(Integer roleId);

    public Integer create(RolePermissionRequest request);

    public void update(Integer id, RolePermissionRequest request);
}
