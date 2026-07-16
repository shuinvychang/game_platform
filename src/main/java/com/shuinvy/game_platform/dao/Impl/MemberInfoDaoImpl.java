package com.shuinvy.game_platform.dao.Impl;

import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.constant.Table;
import com.shuinvy.game_platform.dao.MemberInfoDao;
import com.shuinvy.game_platform.dto.MemberInfoRequest;
import com.shuinvy.game_platform.model.MemberInfo;
import com.shuinvy.game_platform.rowmapper.MemberInfoRowMapper;
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
public class MemberInfoDaoImpl implements MemberInfoDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public MemberInfo getById(Integer id) {
        String sql = """
                SELECT * FROM %s
                WHERE id = :id
                AND `status` = :status
                """.formatted(Table.MemberInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.ENABLED);
        List<MemberInfo> list = jdbcTemplate.query(sql, map, new MemberInfoRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public MemberInfo getByMemberId(Integer memberId) {
        String sql = """
                SELECT * FROM %s
                WHERE `member_id` = :memberId
                AND `status` = :status
                LIMIT 1;
                """.formatted(Table.MemberInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("status", Status.ENABLED);
        List<MemberInfo> list = jdbcTemplate.query(sql, map, new MemberInfoRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<MemberInfo> getList() {
        String sql = """
                SELECT * FROM %s
                WHERE `status` = :status
                """.formatted(Table.MemberInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("status", Status.ENABLED);
        return jdbcTemplate.query(sql, map, new MemberInfoRowMapper());
    }

    @Override
    public Integer create(Integer memberId, MemberInfoRequest request, String ip) {
        String sql = """
                INSERT INTO %s (
                    `member_id`,
                    `name`,
                    `ip`,
                    `status`,
                    `created`
                ) VALUES (
                    :member_id,
                    :name,
                    :ip,
                    :status,
                    :created
                )
                """.formatted(Table.MemberInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("member_id", memberId);
        map.put("name", request.getName());
        map.put("ip", ip);
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
    public void update(Integer id, MemberInfoRequest request) {
        String sql = """
                UPDATE %s SET
                    `name` =  :name,
                    `point`  = :point,
                    `modified` = :modified
                WHERE id = :id
                """.formatted(Table.MemberInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", request.getName());
        map.put("point", request.getPoint());
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
                """.formatted(Table.MemberInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.DELETED);
        map.put("modified",  new Date());
        jdbcTemplate.update(sql, map);
    }
}
