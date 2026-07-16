package com.shuinvy.game_platform.service;

import com.shuinvy.game_platform.dto.RoleRequest;
import com.shuinvy.game_platform.model.Role;

import java.util.List;

public interface RoleService {

    Role getById(Integer id);

    List<Role> getList();

    boolean checkExists(String name, Integer id);

    Integer create(RoleRequest request);

    void update(Integer id, RoleRequest request);

    void delete(Integer id);
}
