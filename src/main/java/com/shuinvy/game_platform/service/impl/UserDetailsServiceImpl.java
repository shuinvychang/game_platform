package com.shuinvy.game_platform.service.impl;

import com.shuinvy.game_platform.dao.UserDao;
import com.shuinvy.game_platform.model.User;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userDao.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Can't find user: " + username);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .build();
    }
}
