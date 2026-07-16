package com.shuinvy.game_platform.service.impl;

import com.shuinvy.game_platform.dao.RoleDao;
import com.shuinvy.game_platform.dto.RoleRequest;
import com.shuinvy.game_platform.model.Role;
import com.shuinvy.game_platform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role getById(Integer id) {
        return roleDao.getById(id);
    }

    @Override
    public List<Role> getList() {
        return roleDao.getList();
    }

    @Override
    public boolean checkExists(String name, Integer id) {
        Role role = roleDao.getByName(name);
        if (role == null) {
            return false;
        }
        if (id == 0) {
            return true;
        }
        return !role.getId().equals(id);
    }

    @Override
    public Integer create(RoleRequest request) {
        return roleDao.create(request);
    }

    @Override
    public void update(Integer id, RoleRequest request) {
        Role oldRole = roleDao.getById(id);
        if (request.getName() == null) {
            request.setName(oldRole.getName());
        }
        if (request.getStatus() == null) {
            request.setStatus(oldRole.getStatus());
        }
        roleDao.update(id, request);
    }

    @Override
    public void delete(Integer id) {
        roleDao.delete(id);
    }
}
