package com.shuinvy.game_platform.service;

import com.shuinvy.game_platform.dto.UserRequest;
import com.shuinvy.game_platform.dto.UserResponse;
import com.shuinvy.game_platform.model.User;

import java.util.List;

public interface UserService {

    User getById(Integer id);

    User getByUsername(String username);

    boolean checkExists(String username, Integer id);

    List<UserResponse> getList();

    Integer create(UserRequest request);

    void update(Integer id, UserRequest request);

    void delete(Integer id);
}
