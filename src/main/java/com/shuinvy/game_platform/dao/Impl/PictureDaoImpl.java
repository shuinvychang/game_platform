package com.shuinvy.game_platform.dao.Impl;

import com.shuinvy.game_platform.constant.ResourceType;
import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.constant.Table;
import com.shuinvy.game_platform.dao.PictureDao;
import com.shuinvy.game_platform.dto.PictureRequest;
import com.shuinvy.game_platform.model.Picture;
import com.shuinvy.game_platform.rowmapper.PictureRowMapper;
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
public class PictureDaoImpl implements PictureDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Picture getById(Integer id) {
        String sql = """
                SELECT * FROM %s
                WHERE id = :id
                AND `status` = :status
                """.formatted(Table.Picture);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.ENABLED);
        List<Picture> list = jdbcTemplate.query(sql, map, new PictureRowMapper());
        if  (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Picture> getList() {
        String sql = """
                SELECT * FROM %s
                WHERE `status` = :status
                """.formatted(Table.Picture);
        Map<String, Object> map = new HashMap<>();
        map.put("status", Status.ENABLED);
        return jdbcTemplate.query(sql, map, new PictureRowMapper());
    }

    @Override
    public List<Picture> getListByIds(List<Integer> ids) {
        String sql = """
                SELECT * FROM %s
                where `id` in (:ids)
                """.formatted(Table.Picture);
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        return jdbcTemplate.query(sql, map, new PictureRowMapper());
    }

    @Override
    public List<Picture> getListByGameId(Integer gameId) {
        String sql = """
                SELECT * FROM %s
                WHERE `status` = :status
                AND `reference_id` = :gameId
                AND `reference_type` = :type
                """.formatted(Table.Picture);
        Map<String, Object> map = new HashMap<>();
        map.put("status", Status.ENABLED);
        map.put("gameId", gameId);
        map.put("type", ResourceType.Game);
        return jdbcTemplate.query(sql, map, new PictureRowMapper());
    }

    @Override
    public Integer create(PictureRequest request) {
        String sql = """
                INSERT INTO %s (
                    `path`,
                    `reference_id`,
                    `reference_type`,
                    `content_type`,
                    `status`,
                    `created`
                ) VALUES (
                    :path,
                    :referenceId,
                    :referenceType,
                    :contentType,
                    :status,
                    :created
                )
                """.formatted(Table.Picture);
        Map<String, Object> map = new HashMap<>();
        map.put("path", request.getPath());
        map.put("referenceId", request.getReferenceId());
        map.put("referenceType", request.getReferenceType());
        map.put("contentType", request.getContentType());
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
    public void update(Integer id, PictureRequest request) {
        String sql = """
                UPDATE %s SET
                    `path`  = :path,
                    `reference_id` = :referenceId,
                    `reference_type` = :referenceType,
                    `content_type` = :contentType,
                    `modified` = :modified
                WHERE id = :id
                """.formatted(Table.Picture);
        Map<String, Object> map = new HashMap<>();
        map.put("path", request.getPath());
        map.put("referenceId", request.getReferenceId());
        map.put("referenceType", request.getReferenceType());
        map.put("contentType", request.getContentType());
        map.put("modified", new Date());
        map.put("id", id);
        jdbcTemplate.update(sql, map);
    }

    @Override
    public void updateIdsByGameId(List<Integer> ids, Integer gameId) {
        String sql = """
                UPDATE %s SET
                    `reference_id` = :gameId,
                    `reference_type` = :referenceType,
                    `status` = :status,
                    `modified` = :modified
                WHERE id IN (:ids)
                """.formatted(Table.Picture);
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        map.put("gameId", gameId);
        map.put("referenceType", ResourceType.Game);
        map.put("status", Status.ENABLED);
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
                """.formatted(Table.Picture);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.DELETED);
        map.put("modified", new Date());
        jdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        String sql = """
                UPDATE %s SET
                    `status` = :status,
                    `modified` = :modified
                WHERE id in (:ids)
                """.formatted(Table.Picture);
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        map.put("status", Status.DELETED);
        map.put("modified", new Date());
        jdbcTemplate.update(sql, map);
    }
}
