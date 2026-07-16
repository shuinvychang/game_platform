package com.shuinvy.game_platform.dao.Impl;

import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.constant.Table;
import com.shuinvy.game_platform.dao.RoleDao;
import com.shuinvy.game_platform.dto.RoleRequest;
import com.shuinvy.game_platform.model.Role;
import com.shuinvy.game_platform.rowmapper.RoleRowMapper;
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
public class RoleDaoImpl implements RoleDao {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Role getById(Integer id) {
        String sql = """
                SELECT * FROM %s WHERE id = :id
                AND `status` = :status
                """.formatted(Table.Role);
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("status",Status.ENABLED);
        List<Role> list = jdbcTemplate.query(sql, map, new RoleRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Role getByName(String name) {
        String sql = """
                SELECT * FROM %s
                WHERE `name` = :name
                AND `status` = :status
                """.formatted(Table.Role);
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("status",Status.ENABLED);
        List<Role> list = jdbcTemplate.query(sql, map, new RoleRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Role> getList() {
        String sql = "SELECT * FROM %s WHERE `status` = :status".formatted(Table.Role);
        Map<String, Object> map = new HashMap<>();
        map.put("status", Status.ENABLED);
        return jdbcTemplate.query(sql, map, new RoleRowMapper());
    }

    @Override
    public Integer create(RoleRequest request) {
        String sql = """
                INSERT INTO %s (
                    `name`,
                    `status`,
                    `created`
                )  VALUES (
                    :name,
                    :status,
                    :created
                )
                """.formatted(Table.Role);
        Map<String,Object> map = new HashMap<>();
        map.put("name",request.getName());
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
    public void update(Integer id, RoleRequest request) {
        String sql = """
                UPDATE %s SET
                    `name`  = :name,
                    `status` = :status,
                    `modified` = :modified
                WHERE id = :id
                """.formatted(Table.Role);
        Map<String,Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", request.getName());
        map.put("status", request.getStatus());
        map.put("modified", new Date());
        jdbcTemplate.update(sql, map);
    }

    @Override
    public void delete(Integer id) {
        String sql = """
                UPDATE %s SET
                    `status` = :status,
                    `deleted` = :deleted
                WHERE id = :id
                """.formatted(Table.Role);
        Map<String,Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.DELETED);
        map.put("deleted", new Date());
        jdbcTemplate.update(sql, map);
    }
}
