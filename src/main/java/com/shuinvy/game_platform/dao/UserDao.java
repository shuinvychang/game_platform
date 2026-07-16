package com.shuinvy.game_platform.dao;

import com.shuinvy.game_platform.dto.UserRequest;
import com.shuinvy.game_platform.model.User;

import java.util.List;

public interface UserDao {

    User getById(Integer id);

    User getByUsername(String username);

    List<User> getList();

    Integer create(UserRequest request);

    void update(Integer id, UserRequest request);

    void delete(Integer id);
}
