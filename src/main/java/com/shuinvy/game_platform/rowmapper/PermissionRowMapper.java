package com.shuinvy.game_platform.rowmapper;

import com.shuinvy.game_platform.model.Permission;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PermissionRowMapper implements RowMapper<Permission> {

    @Override
    public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
        Permission permission = new Permission();
        permission.setId(rs.getInt("id"));
        permission.setPage(rs.getString("page"));
        permission.setButton(rs.getString("button"));
        permission.setStatus(rs.getInt("status"));
        permission.setCreated(rs.getTimestamp("created"));
        permission.setModified(rs.getTimestamp("modified"));
        return permission;
    }
}
