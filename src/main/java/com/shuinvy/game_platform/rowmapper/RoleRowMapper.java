package com.shuinvy.game_platform.rowmapper;

import com.shuinvy.game_platform.model.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        role.setId(rs.getInt("id"));
        role.setName(rs.getString("name"));
        role.setStatus(rs.getInt("status"));
        role.setCreated(rs.getTimestamp("created"));
        role.setModified(rs.getTimestamp("modified"));
        return role;
    }
}
