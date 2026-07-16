package com.shuinvy.game_platform.dao.Impl;

import com.shuinvy.game_platform.constant.Table;
import com.shuinvy.game_platform.dao.NotificationLogDao;
import com.shuinvy.game_platform.dto.NotificationLogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationLogDaoImpl implements NotificationLogDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Integer create(NotificationLogRequest request) {
        String sql = """
                INSERT INTO %s (
                    `member_id`,
                    `member_name`,
                    `notify_type`,
                    `email`,
                    `reference_id`,
                    `memo`,
                    `created`
                ) VALUES (
                    :memberId,
                    :memberName,
                    :notifyType,
                    :email,
                    :referenceId,
                    :memo,
                    :created
                )
                """.formatted(Table.NotificationLog);
        Map<String,Object> map = new HashMap<>();
        map.put("memberId",request.getMemberId());
        map.put("memberName",request.getMemberName());
        map.put("notifyType",request.getNotifyType());
        map.put("email",request.getEmail());
        map.put("referenceId",request.getReferenceId());
        map.put("memo",request.getMemo());
        map.put("created", new Date());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, map);

        int newId = 0;
        if (keyHolder.getKey() != null) {
            newId = keyHolder.getKey().intValue();
        }
        return newId;
    }
}
