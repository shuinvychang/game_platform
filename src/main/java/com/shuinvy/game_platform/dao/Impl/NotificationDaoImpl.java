package com.shuinvy.game_platform.dao.Impl;

import com.shuinvy.game_platform.constant.NotifyType;
import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.constant.Table;
import com.shuinvy.game_platform.dao.NotificationDao;
import com.shuinvy.game_platform.dto.NotificationRequest;
import com.shuinvy.game_platform.dto.NotificationResponse;
import com.shuinvy.game_platform.model.Notification;
import com.shuinvy.game_platform.rowmapper.NotificationResponseRowMapper;
import com.shuinvy.game_platform.rowmapper.NotificationRowMapper;
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
public class NotificationDaoImpl implements NotificationDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Notification getById(Integer id) {
        String sql = """
                SELECT * FROM %s
                WHERE id = :id
                """.formatted(Table.Notification);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        List<Notification> list = jdbcTemplate.query(sql, map, new NotificationRowMapper());
        if  (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Notification getByMemberId(Integer memberId) {
        String sql = """
                SELECT * FROM %s
                WHERE member_id = :memberId
                LIMIT 1;
                """.formatted(Table.Notification);
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);
        List<Notification> list = jdbcTemplate.query(sql, map, new NotificationRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Notification> getList() {
        String sql = """
                SELECT * FROM %s
                """.formatted(Table.Notification);
        return jdbcTemplate.query(sql, new NotificationRowMapper());
    }

    @Override
    public List<NotificationResponse> getListWithMember() {
        String sql = """
                SELECT
                n.`id` as id,
                n.`member_id` as member_id,
                n.`is_new_game` as is_new_game,
                n.`created` as created,
                n.`modified` as modified,
                m.`email` as email
                FROM %s n
                LEFT JOIN %s m
                ON n.`member_id` = m.`id`
                """.formatted(Table.Notification, Table.Member);
        return jdbcTemplate.query(sql, new NotificationResponseRowMapper());
    }

    @Override
    public List<NotificationResponse> getListByTypeRefId(
            String type) {
        String field = "";
        switch(type) {
            case NotifyType.New_Game:
                field = "is_new_game";
            default:
        }
        String sql = """
                SELECT
                n.`id` as id,
                n.`member_id` as member_id,
                n.`is_new_game` as is_new_game,
                n.`created` as created,
                n.`modified` as modified,
                m.`email` as email
                FROM %s n
                LEFT JOIN %s m
                ON n.`member_id` = m.`id`
                WHERE n.`%s` = :status
                """.formatted(Table.Notification, Table.Member, field);
        Map<String, Object> map = new HashMap<>();
        map.put("status", Status.ENABLED);
        return jdbcTemplate.query(sql, map, new NotificationResponseRowMapper());
    }

    @Override
    public Integer create(NotificationRequest request) {
        String sql = """
                INSERT INTO %s (
                    `member_id`,
                    `is_new_game`,
                    `created`
                ) VALUES (
                    :member_id,
                    :is_new_game,
                    :created
                )
                """.formatted(Table.Notification);
        Map<String, Object> map = new HashMap<>();
        map.put("member_id", request.getMemberId());
        map.put("is_new_game", request.getIsNewGame());
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
    public void update(Integer id, NotificationRequest request) {
        String sql = """
                UPDATE %s SET
                    `is_new_game` = :is_new_game,
                    `modified` = :modified
                WHERE id = :id
                """.formatted(Table.Notification);
        Map<String, Object> map = new HashMap<>();
        map.put("is_new_game", request.getIsNewGame());
        map.put("modified", new Date());
        map.put("id", id);
        jdbcTemplate.update(sql, map);
    }

    @Override
    public void delete(Integer id) {
        String sql = """
                UPDATE %s SET
                    `is_new_game` = :is_new_game,
                    `modified` = :modified
                WHERE id = :id
                """.formatted(Table.Notification);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("is_new_game", Status.DELETED);
        map.put("modified", new Date());
        jdbcTemplate.update(sql, map);
    }
}
