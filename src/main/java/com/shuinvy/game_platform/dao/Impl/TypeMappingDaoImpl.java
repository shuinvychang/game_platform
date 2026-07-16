package com.shuinvy.game_platform.dao.Impl;

import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.constant.Table;
import com.shuinvy.game_platform.dao.TypeMappingDao;
import com.shuinvy.game_platform.dto.TypeMappingRequest;
import com.shuinvy.game_platform.model.TypeMapping;
import com.shuinvy.game_platform.rowmapper.TypeMappingRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TypeMappingDaoImpl implements TypeMappingDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public TypeMapping getById(Integer id) {
        String sql = """
                SELECT * FROM %s
                WHERE id = :id
                AND `status` = :status
                """.formatted(Table.TypeMapping);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.ENABLED);
        List<TypeMapping> list = jdbcTemplate.query(sql, map, new TypeMappingRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<TypeMapping> getByGameId(Integer gameId) {
        String sql = """
                SELECT * FROM %s
                WHERE `status` = :status
                AND `game_id` = :gameId
                """.formatted(Table.TypeMapping);
        Map<String, Object> map = new HashMap<>();
        map.put("status", Status.ENABLED);
        map.put("gameId", gameId);
        return jdbcTemplate.query(sql, map, new TypeMappingRowMapper());
    }

    @Override
    public List<TypeMapping> getByGameAll(Integer gameId) {
        String sql = """
                SELECT * FROM %s
                WHERE `game_id` = :gameId
                """.formatted(Table.TypeMapping);
        Map<String, Object> map = new HashMap<>();
        map.put("gameId", gameId);
        return jdbcTemplate.query(sql, map, new TypeMappingRowMapper());
    }

    @Override
    public List<TypeMapping> getList() {
        String sql = """
                SELECT * FROM %s
                WHERE `status` = :status
                """.formatted(Table.TypeMapping);
        Map<String, Object> map = new HashMap<>();
        map.put("status", Status.ENABLED);
        return jdbcTemplate.query(sql, map, new TypeMappingRowMapper());
    }

    @Override
    public Integer create(TypeMappingRequest request) {
        String sql = """
                INSERT INTO %s (
                    `game_id`,
                    `type_id`,
                    `status`,
                    `created`
                ) VALUES (
                    :game_id,
                    :type_id,
                    :status,
                    :created
                )
                """.formatted(Table.TypeMapping);
        Map<String, Object> map = new HashMap<>();
        map.put("game_id", request.getGameId());
        map.put("type_id", request.getTypeId());
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
    public void createTypeIdsByGameId(List<Integer> typeIds, Integer gameId) {
        String sql = """
                INSERT INTO %s (
                    `type_id`,
                    `game_id`,
                    `status`,
                    `created`
                ) VALUES (
                    ?,
                    ?,
                    ?,
                    ?
                )
                """.formatted(Table.TypeMapping);
        Timestamp now = Timestamp.from(Instant.now());
        jdbcTemplate.getJdbcTemplate().batchUpdate(
                sql,
                typeIds,
                typeIds.size(),
                (ps, typeId) -> {
                    ps.setInt(1, typeId);
                    ps.setInt(2, gameId);
                    ps.setInt(3, Status.ENABLED);
                    ps.setTimestamp(4, now);
                }
        );
    }

    @Override
    public void update(Integer id, TypeMappingRequest request) {
        String sql = """
                UPDATE %s SET
                    `game_id` = :game_id,
                    `type_id` = :type_id,
                    `status` = :status,
                    `modified` = :modified
                WHERE id = :id
                """.formatted(Table.TypeMapping);
        Map<String, Object> map = new HashMap<>();
        map.put("game_id", request.getGameId());
        map.put("type_id", request.getTypeId());
        map.put("status", request.getStatus());
        map.put("modified", new Date());
        map.put("id", id);
        jdbcTemplate.update(sql, map);
    }

    @Override
    public void updateStatusByIds(List<Integer> ids, Integer status) {
        String sql = """
                UPDATE %s SET
                `status` = :status,
                `modified` = :modified
                WHERE id IN (:ids)
                """.formatted(Table.TypeMapping);
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("modified", new Date());
        map.put("ids", ids);
        jdbcTemplate.update(sql, map);
    }

    @Override
    public void delete(Integer id) {
        String sql = """
                UPDATE %s SET
                    `status` = :status,
                    `modified` = :modified
                WHERE id = :id
                """.formatted(Table.TypeMapping);
        Map<String, Object> map = new HashMap<>();
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
                WHERE id IN (:ids)
                AND `status` = :enableStatus
                """.formatted(Table.TypeMapping);
        Map<String, Object> map = new HashMap<>();
        map.put("status", Status.DELETED);
        map.put("modified", new Date());
        map.put("ids", ids);
        map.put("enableStatus", Status.ENABLED);
        jdbcTemplate.update(sql, map);
    }
}
