package com.shuinvy.game_platform.service.impl;

import com.shuinvy.game_platform.dao.UserDao;
import com.shuinvy.game_platform.dto.UserRequest;
import com.shuinvy.game_platform.dto.UserResponse;
import com.shuinvy.game_platform.model.User;
import com.shuinvy.game_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getById(Integer id) {
        return userDao.getById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public boolean checkExists(String username, Integer id) {
        User user = getByUsername(username);
        if (user == null) {
            return false;
        }
        if (id == 0) {
            return true;
        }
        return !user.getId().equals(id);
    }

    @Override
    public List<UserResponse> getList() {
        List<User> userList = userDao.getList();
        return userList.stream()
                .map(UserResponse::new)
                .toList();
    }

    @Override
    public Integer create(UserRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getRoleId() == null) {
            request.setRoleId(1);
        }
        return userDao.create(request);
    }

    @Override
    public void update(Integer id, UserRequest request) {
        User oldUser = getById(id);
        if (request.getUsername() == null) {
            request.setUsername(oldUser.getUsername());
        }
        if (request.getPassword() == null) {
            request.setPassword(oldUser.getPassword());
        } else {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getRoleId() == null) {
            request.setRoleId(oldUser.getRoleId());
        }
        if (request.getStatus() == null) {
            request.setStatus(oldUser.getStatus());
        }
        userDao.update(id, request);
    }

    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }
}
