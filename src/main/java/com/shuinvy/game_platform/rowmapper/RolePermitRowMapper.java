package com.shuinvy.game_platform.rowmapper;

import com.shuinvy.game_platform.model.RolePermission;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RolePermitRowMapper implements RowMapper<RolePermission> {

    @Override
    public RolePermission mapRow(ResultSet rs, int rowNum) throws SQLException {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setId(rs.getInt("id"));
        rolePermission.setRoleId(rs.getInt("role_id"));
        rolePermission.setPermissionId(rs.getInt("permit_id"));
        rolePermission.setStatus(rs.getInt("status"));
        rolePermission.setCreated(rs.getTimestamp("created"));
        rolePermission.setModified(rs.getTimestamp("modified"));
        return rolePermission;
    }
}
