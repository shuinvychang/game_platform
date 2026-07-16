package com.shuinvy.game_platform.dao.Impl;

import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.constant.Table;
import com.shuinvy.game_platform.dao.PaymentLogDao;
import com.shuinvy.game_platform.dto.PaymentLogRequest;
import com.shuinvy.game_platform.model.PaymentLog;
import com.shuinvy.game_platform.rowmapper.PaymentLogRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentLogDaoImpl implements PaymentLogDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public PaymentLog getById(Integer id) {
        String sql = """
                SELECT * FROM %s
                WHERE id = :id
                AND `status` = :status
                """.formatted(Table.PaymentLog);
        Map<String,Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.ENABLED);
        List<PaymentLog> list = jdbcTemplate.query(sql, map, new PaymentLogRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public PaymentLog getByMemberGameId(Integer memberId, Integer gameId) {
        String sql = """
                SELECT * FROM %s
                WHERE `member_id` = :memberId
                AND `game_id` = :gameId
                LIMIT 1
                """.formatted(Table.PaymentLog);
        Map<String,Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("gameId", gameId);
        List<PaymentLog> list = jdbcTemplate.query(sql, map, new PaymentLogRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<PaymentLog> getList() {
        String sql = """
                SELECT * FROM %s
                WHERE `status` = :status
                """.formatted(Table.PaymentLog);
        Map<String,Object> map = new HashMap<>();
        map.put("status", Status.ENABLED);
        return jdbcTemplate.query(sql, map, new PaymentLogRowMapper());
    }

    @Override
    public Integer create(PaymentLogRequest request, String memberName, String gameName,
                      BigDecimal point, Integer status) {
        String sql = """
                INSERT INTO %s(
                    `member_id`,
                    `member_name`,
                    `game_id`,
                    `game_name`,
                    `point`,
                    `status`,
                    `created`
                ) VALUES (
                    :member_id,
                    :member_name,
                    :game_id,
                    :game_name,
                    :point,
                    :status,
                    :created
                )
                """.formatted(Table.PaymentLog);
        Map<String,Object> map = new HashMap<>();
        map.put("member_id", request.getMemberId());
        map.put("member_name", memberName);
        map.put("game_id", request.getGameId());
        map.put("game_name", gameName);
        map.put("point", point);
        map.put("status", status);
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
    public void delete(Integer id) {
        String sql = """
                UPDATE %s SET
                    `status` = :status
                    `modified` = :modified
                WHERE id = :id
                """.formatted(Table.PaymentLog);
        Map<String,Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.DELETED);
        map.put("modified", new Date());
        jdbcTemplate.update(sql, map);
    }
}
