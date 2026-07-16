package com.shuinvy.game_platform.dao.Impl;

import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.constant.Table;
import com.shuinvy.game_platform.dao.PermissionDao;
import com.shuinvy.game_platform.dto.PermissionRequest;
import com.shuinvy.game_platform.model.Permission;
import com.shuinvy.game_platform.rowmapper.PermissionRowMapper;
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
public class PermissionDaoImpl implements PermissionDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Permission getById(Integer id) {
        String sql = """
                SELECT * FROM %s
                WHERE id = :id
                AND `status` = :status
                """.formatted(Table.Permission);
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("status", Status.ENABLED);
        List<Permission> list = jdbcTemplate.query(sql, map, new PermissionRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Permission getByPageButton(String page, String button) {
        String sql = """
                SELECT * FROM %s
                WHERE `status` = :status
                AND `page` = :page
                """.formatted(Table.Permission);
        if (button.isEmpty()) {
            sql += " AND `button` IS NULL";
        } else {
            sql += " AND `button` = :button";
        }
        sql += " LIMIT 1";
        Map<String,Object> map = new HashMap<>();
        map.put("status", Status.ENABLED);
        map.put("page", page);
        map.put("button", button);
        List<Permission> list = jdbcTemplate.query(sql, map, new PermissionRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Permission> getList() {
        String sql = """
                SELECT * FROM %s
                WHERE `status` = :status
                """.formatted(Table.Permission);
        Map<String,Object> map = new HashMap<>();
        map.put("status", Status.ENABLED);
        return jdbcTemplate.query(sql, map, new PermissionRowMapper());
    }

    @Override
    public Integer create(PermissionRequest request) {
        String sql = """
                INSERT INTO %s(
                    `page`,
                    `button`,
                    `status`,
                    `created`
                ) VALUES (
                    :page,
                    :button,
                    :status,
                    :created
                )
                """.formatted(Table.Permission);
        Map<String,Object> map = new HashMap<>();
        map.put("page",request.getPage());
        map.put("button",request.getButton());
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
    public void update(Integer id, PermissionRequest request) {
        String sql = """
                UPDATE %s SET
                    `page` = :page,
                    `button` = :button,
                    `status` = :status,
                    `modified` = :modified
                WHERE id = :id
                """.formatted(Table.Permission);
        Map<String,Object> map = new HashMap<>();
        map.put("id", id);
        map.put("page", request.getPage());
        map.put("button", request.getButton());
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
                """.formatted(Table.Permission);
        Map<String,Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.DELETED);
        map.put("deleted", new Date());
        jdbcTemplate.update(sql, map);
    }
}
