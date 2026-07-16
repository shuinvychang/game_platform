package com.shuinvy.game_platform.dao;

import com.shuinvy.game_platform.dto.RoleRequest;
import com.shuinvy.game_platform.model.Role;

import java.util.List;

public interface RoleDao {

    Role getById(Integer id);

    Role getByName(String name);

    List<Role> getList();

    Integer create (RoleRequest request);

    void update(Integer id, RoleRequest request);

    void delete(Integer id);
}
