package com.shuinvy.game_platform.service.impl;

import com.shuinvy.game_platform.dao.PermissionDao;
import com.shuinvy.game_platform.dao.RoleDao;
import com.shuinvy.game_platform.dao.RolePermitDao;
import com.shuinvy.game_platform.dto.RolePermissionRequest;
import com.shuinvy.game_platform.dto.RolePermitResponse;
import com.shuinvy.game_platform.model.Permission;
import com.shuinvy.game_platform.model.Role;
import com.shuinvy.game_platform.model.RolePermission;
import com.shuinvy.game_platform.service.RolePermitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RolePermitServiceImpl implements RolePermitService {

    @Autowired
    private RolePermitDao rolePermitDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public RolePermitResponse getById(Integer id) {
        RolePermission info = rolePermitDao.getById(id);
        Role role = roleDao.getById(info.getRoleId());
        Permission permission = permissionDao.getById(info.getPermissionId());
        return new RolePermitResponse(
                role,
                permission,
                info.getStatus());
    }

    @Override
    public RolePermission getExists(Integer roleId, Integer permitId) {
        return rolePermitDao.getByRolePermitId(roleId, permitId);
    }

    @Override
    public List<RolePermitResponse> getList() {
        List<RolePermission> infos = rolePermitDao.getList();
        return getFullDataList(infos);
    }

    @Override
    public List<RolePermitResponse> getListByRoleId(Integer roleId) {
        List<RolePermission> infos = rolePermitDao.getListByRoleId(roleId);
        return getFullDataList(infos);
    }

    @Override
    public Integer create(RolePermissionRequest request) {
        if (request.getStatus() == null) {
            request.setStatus(1);
        }
        return rolePermitDao.create(request);
    }

    @Override
    public void update(Integer id, RolePermissionRequest request) {
        if (request.getStatus() == null) {
            request.setStatus(1);
        }
        rolePermitDao.update(id, request);
    }

    private Map<Integer, Role> getRoleMapper() {
        List<Role> list = roleDao.getList();
        Map<Integer, Role> map = new HashMap<>();
        for (Role role : list) {
            map.put(role.getId(), role);
        }
        return map;
    }

    private Map<Integer, Permission> getPermitMapper() {
        List<Permission> list = permissionDao.getList();
        Map<Integer, Permission> map = new HashMap<>();
        for (Permission permission : list) {
            map.put(permission.getId(), permission);
        }
        return map;
    }

    private List<RolePermitResponse> getFullDataList(List<RolePermission> infos) {
        List<RolePermitResponse> result = new ArrayList<>();
        Map<Integer, Role> roleMap = getRoleMapper();
        Map<Integer, Permission> permitMap = getPermitMapper();
        Role eachRole = null;
        Permission eachPermit = null;
        for (RolePermission info : infos) {
            if (roleMap.containsKey(info.getRoleId())) {
                eachRole  = roleMap.get(info.getRoleId());
            }
            if (permitMap.containsKey(info.getPermissionId())) {
                eachPermit  = permitMap.get(info.getPermissionId());
            }
            if (eachRole == null || eachPermit == null) {
                continue;
            }
            result.add(new RolePermitResponse(
                    eachRole,
                    eachPermit,
                    info.getStatus()));
        }
        return result;
    }
}
