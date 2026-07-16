package com.shuinvy.game_platform.dao.Impl;

import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.constant.Table;
import com.shuinvy.game_platform.dao.GameDao;
import com.shuinvy.game_platform.dto.GameRequest;
import com.shuinvy.game_platform.model.Game;
import com.shuinvy.game_platform.rowmapper.GameRowMapper;
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
public class GameDaoImpl implements GameDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Game getById(Integer id) {
        String sql = """
                SELECT * FROM %s
                WHERE id = :id
                AND `status` = :status
                """.formatted(Table.Game);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.ENABLED);
        List<Game> list = jdbcTemplate.query(sql, map, new GameRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Game getByName(String name) {
        String sql = """
                SELECT * FROM %s
                WHERE name = :name
                AND `status` = :status
                LIMIT 1;
                """.formatted(Table.Game);
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("status", Status.ENABLED);
        List<Game> list = jdbcTemplate.query(sql, map, new GameRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Game> getList() {
        String sql = """
                SELECT * FROM %s
                WHERE `status` = :status
                """.formatted(Table.Game);
        Map<String, Object> map = new HashMap<>();
        map.put("status", Status.ENABLED);
        return jdbcTemplate.query(sql, map, new GameRowMapper());
    }

    @Override
    public Integer create(GameRequest request) {
        String sql = """
                INSERT INTO %s (
                    `name`,
                    `info`,
                    `description`,
                    `price`,
                    `is_published`,
                    `published`,
                    `status`,
                    `created`
                ) VALUES (
                    :name,
                    :info,
                    :description,
                    :price,
                    :is_published,
                    :published,
                    :status,
                    :created
                )
                """.formatted(Table.Game);
        Map<String, Object> map = new HashMap<>();
        map.put("name", request.getName());
        map.put("info", request.getInfo());
        map.put("description", request.getDescription());
        map.put("price", request.getPrice());
        map.put("is_published", request.getIsPublished());
        map.put("published", request.getPublished());
        map.put("status", Status.ENABLED);
        map.put("created", new Date());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int newId = 0;
        if  (keyHolder.getKey() != null) {
            newId = keyHolder.getKey().intValue();
        }
        return newId;
    }

    @Override
    public void update(Integer id, GameRequest request) {
        String sql = """
                UPDATE %s SET
                    `name`  = :name,
                    `info`  = :info,
                    `description` = :description,
                    `price` = :price,
                    `is_published` = :is_published,
                    `published` = :published,
                    `status` = :status,
                    `modified` = :modified
                WHERE id = :id
                """.formatted(Table.Game);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", request.getName());
        map.put("info", request.getInfo());
        map.put("description", request.getDescription());
        map.put("price", request.getPrice());
        map.put("is_published", request.getIsPublished());
        map.put("published", request.getPublished());
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
                """.formatted(Table.Game);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.DELETED);
        map.put("deleted", new Date());
        jdbcTemplate.update(sql, map);
    }
}
