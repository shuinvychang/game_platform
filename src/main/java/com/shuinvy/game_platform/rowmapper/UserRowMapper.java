package com.shuinvy.game_platform.rowmapper;

import com.shuinvy.game_platform.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRoleId(rs.getInt("role_id"));
        user.setStatus(rs.getInt("status"));
        user.setCreated(rs.getTimestamp("created"));
        user.setModified(rs.getTimestamp("modified"));
        return user;
    }
}
