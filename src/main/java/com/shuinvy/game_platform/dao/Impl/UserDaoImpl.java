package com.shuinvy.game_platform.dao.Impl;

import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.constant.Table;
import com.shuinvy.game_platform.dao.UserDao;
import com.shuinvy.game_platform.dto.UserRequest;
import com.shuinvy.game_platform.model.User;
import com.shuinvy.game_platform.rowmapper.UserRowMapper;
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
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public User getById(Integer id) {
        String sql = """
                SELECT * FROM %s
                WHERE id = :id
                AND status = :status
                LIMIT 1;
                """.formatted(Table.User);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.ENABLED);

        List<User> list = jdbcTemplate.query(sql, map, new UserRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public User getByUsername(String username) {
        String sql = """
                SELECT * FROM %s
                WHERE `username` = :username
                AND `status` = :status
                LIMIT 1;
                """.formatted(Table.User);
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("status", Status.ENABLED);
        List<User> list = jdbcTemplate.query(sql, map, new UserRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<User> getList() {
        String sql = "SELECT * FROM %s WHERE status = :status".formatted(Table.User);
        Map<String, Object> map = new HashMap<>();
        map.put("status", Status.ENABLED);
        return jdbcTemplate.query(sql, map, new UserRowMapper());
    }

    @Override
    public Integer create(UserRequest request) {
        String sql = """
                INSERT INTO %s (
                    `username`,
                    `password`,
                    `role_id`,
                    `status`,
                    `created`
                ) VALUES (
                    :username,
                    :password,
                    :role_id,
                    :status,
                    :created
                )
                """.formatted(Table.User);
        Map<String, Object> map = new HashMap<>();
        map.put("username", request.getUsername());
        map.put("password", request.getPassword());
        map.put("role_id", request.getRoleId());
        map.put("status", Status.ENABLED);
        map.put("created", new Date());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int newId = 0;
        if (keyHolder.getKey() != null) {
            newId = keyHolder.getKey().intValue();
        }
        return  newId;
    }

    @Override
    public void update(Integer id, UserRequest request) {
        String sql = """
                UPDATE %s SET
                    `username` = :username,
                    `password` = :password,
                    `role_id` = :role_id,
                    `status` = :status,
                    `modified` = :modified
                WHERE id = :id
                """.formatted(Table.User);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        map.put("username", request.getUsername());
        map.put("password", request.getPassword());
        map.put("role_id", request.getRoleId());
        map.put("status", request.getStatus());
        map.put("modified", new Date());

        jdbcTemplate.update(sql, map);
    }

    @Override
    public void delete(Integer id) {
        String sql = """
                Update %s SET
                    `status` = :status,
                    `deleted` = :deleted
                WHERE id = :id
                """.formatted(Table.User);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.DELETED);
        map.put("deleted", new Date());

        jdbcTemplate.update(sql, map);
    }
}
