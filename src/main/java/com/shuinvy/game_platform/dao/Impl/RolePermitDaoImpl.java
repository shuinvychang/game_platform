package com.shuinvy.game_platform.dao.Impl;

import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.constant.Table;
import com.shuinvy.game_platform.dao.RolePermitDao;
import com.shuinvy.game_platform.dto.RolePermissionRequest;
import com.shuinvy.game_platform.model.RolePermission;
import com.shuinvy.game_platform.rowmapper.RolePermitRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RolePermitDaoImpl implements RolePermitDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public RolePermission getById(Integer id) {
        String sql = """
                SELECT * FROM %s
                WHERE id = :id
                """.formatted(Table.RolePermit);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        List<RolePermission> list = jdbcTemplate.query(sql, map, new RolePermitRowMapper());
        if  (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public RolePermission getByRolePermitId(Integer roleId, Integer permitId) {
        String sql = """
                SELECT * FROM %s
                WHERE role_id = :roleId
                AND `permit_id` = :permitId
                LIMIT 1
                """.formatted(Table.RolePermit);
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("permitId", permitId);
        List<RolePermission> list = jdbcTemplate.query(sql, map, new RolePermitRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<RolePermission> getList() {
        String sql = """
                SELECT * FROM %s
                """.formatted(Table.RolePermit);
        return  jdbcTemplate.query(sql, new RolePermitRowMapper());
    }

    @Override
    public List<RolePermission> getListByRoleId(Integer roleId) {
        String sql = """
                SELECT * FROM %s
                WHERE `role_id`  = :roleId
                """.formatted(Table.RolePermit);
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        return jdbcTemplate.query(sql, map, new RolePermitRowMapper());
    }

    @Override
    public Integer create(RolePermissionRequest request) {
        String sql = """
                INSERT INTO %s (
                    `role_id`,
                    `permit_id`,
                    `status`,
                    `created`
                ) VALUES (
                    :role_id,
                    :permit_id,
                    :status,
                    :created
                )
                """.formatted(Table.RolePermit);
        Map<String, Object> map = new HashMap<>();
        map.put("role_id", request.getRoleId());
        map.put("permit_id", request.getPermissionId());
        map.put("status", Status.ENABLED);
        map.put("created", new Date());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int newId = 0;
        if (keyHolder.getKey() != null) {
            newId = keyHolder.getKey().intValue();
        }
        return newId;
    }

    @Override
    public void update(Integer id, RolePermissionRequest request) {
        String sql = """
                UPDATE %s SET
                    `role_id` = :role_id,
                    `permit_id` = :permit_id,
                    `status` = :status,
                    `modified` = :modified
                WHERE id = :id
                """.formatted(Table.RolePermit);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("role_id", request.getRoleId());
        map.put("permit_id", request.getPermissionId());
        map.put("status", request.getStatus());
        map.put("modified", new Date());
        jdbcTemplate.update(sql, map);
    }

    @Override
    public void delete(Integer id) {
        String sql = """
                UPDATE %s SET
                `status` = :status,
                `modified` = :modified
                WHERE id = :id
                """.formatted(Table.RolePermit);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.DELETED);
        map.put("modified", new Date());
        jdbcTemplate.update(sql, map);
    }
}
