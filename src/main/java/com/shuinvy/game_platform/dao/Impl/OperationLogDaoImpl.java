package com.shuinvy.game_platform.dao.Impl;

import com.shuinvy.game_platform.constant.Table;
import com.shuinvy.game_platform.dao.OperationLogDao;
import com.shuinvy.game_platform.model.OperationLog;
import com.shuinvy.game_platform.rowmapper.OperationLogRowMapper;
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
public class OperationLogDaoImpl implements OperationLogDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<OperationLog> getList() {
        String sql = """
                SELECT * FROM %s
                """.formatted(Table.OperationLog);
        return jdbcTemplate.query(sql, new OperationLogRowMapper());
    }

    @Override
    public Integer create(Integer userId, String username,
            Integer type, String path,
            String parameter, String result, String memo) {
        String sql = """
                INSERT INTO %s (
                    `user_id`,
                    `username`,
                    `type`,
                    `path`,
                    `parameter`,
                    `result`,
                    `memo`,
                    `created`
                ) VALUES (
                    :userId,
                    :username,
                    :type,
                    :path,
                    :parameter,
                    :result,
                    :memo,
                    :created
                )
                """.formatted(Table.OperationLog);
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("username", username);
        map.put("type", type);
        map.put("path", path);
        map.put("parameter", parameter);
        map.put("result", result);
        map.put("memo", memo);
        map.put("created", new Date());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int newId = 0;
        if (keyHolder.getKey() != null) {
            newId = keyHolder.getKey().intValue();
        }
        return newId;
    }
}
