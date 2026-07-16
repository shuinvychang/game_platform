package com.shuinvy.game_platform.dao.Impl;

import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.constant.Table;
import com.shuinvy.game_platform.dao.GameTypeDao;
import com.shuinvy.game_platform.dto.GameTypeRequest;
import com.shuinvy.game_platform.model.GameType;
import com.shuinvy.game_platform.rowmapper.GameTypeRowMapper;
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
public class GameTypeDaoImpl implements GameTypeDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public GameType getById(Integer id) {
        String sql = """
                SELECT * FROM %s
                WHERE id = :id
                AND `status` = :status
                """.formatted(Table.GameType);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.ENABLED);
        List<GameType> list = jdbcTemplate.query(sql, map, new GameTypeRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<GameType> getList() {
        String sql = """
                SELECT * FROM %s
                WHERE `status` = :status
                """.formatted(Table.GameType);
        Map<String, Object> map = new HashMap<>();
        map.put("status", Status.ENABLED);
        return jdbcTemplate.query(sql, map, new GameTypeRowMapper());
    }

    @Override
    public List<GameType> getListByIds(List<Integer> ids) {
        String sql = """
                SELECT * FROM %s
                WHERE `status` = :status
                AND `id` IN (:ids)
                """.formatted(Table.GameType);
        Map<String, Object> map = new HashMap<>();
        map.put("status", Status.ENABLED);
        map.put("ids", ids);
        return jdbcTemplate.query(sql, map, new GameTypeRowMapper());
    }

    @Override
    public Integer create(GameTypeRequest request) {
        String sql = """
                INSERT INTO %s (
                    `name`,
                    `code`,
                    `status`,
                    `created`
                ) VALUES (
                    :name,
                    :code,
                    :status,
                    :created
                )
                """.formatted(Table.GameType);
        Map<String, Object> map = new HashMap<>();
        map.put("name", request.getName());
        map.put("code", request.getCode());
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
    public void update(Integer id, GameTypeRequest request) {
        String sql = """
                UPDATE %s SET
                    `name` = :name,
                    `code` = :code,
                    `status` = :status,
                    `modified` = :modified
                WHERE id = :id
                """.formatted(Table.GameType);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", request.getName());
        map.put("code", request.getCode());
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
                """.formatted(Table.GameType);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.DELETED);
        map.put("modified", new Date());
        jdbcTemplate.update(sql, map);
    }
}
