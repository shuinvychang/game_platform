package com.shuinvy.game_platform.dao.Impl;

import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.constant.Table;
import com.shuinvy.game_platform.dao.MemberDao;
import com.shuinvy.game_platform.dto.MemberRequest;
import com.shuinvy.game_platform.dto.MemberResponse;
import com.shuinvy.game_platform.model.Member;
import com.shuinvy.game_platform.rowmapper.MemberResponseRowMapper;
import com.shuinvy.game_platform.rowmapper.MemberRowMapper;
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
public class MemberDaoImpl implements MemberDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Member getById(Integer id) {
        String sql = """
                SELECT * FROM %s
                WHERE id = :id
                AND `status` = :status
                """.formatted(Table.Member);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.ENABLED);
        List<Member> list = jdbcTemplate.query(sql, map, new MemberRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Member getByUsername(String username) {
        String sql = """
                SELECT * FROM %s
                WHERE `username` = :username
                AND `status` = :status
                LIMIT 1;
                """.formatted(Table.Member);
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("status", Status.ENABLED);
        List<Member> list = jdbcTemplate.query(sql, map, new MemberRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Member> getList() {
        String sql = """
                SELECT * FROM %s
                WHERE `status` = :status
                """.formatted(Table.Member);
        Map<String, Object> map = new HashMap<>();
        map.put("status", Status.ENABLED);
        return jdbcTemplate.query(sql, map, new MemberRowMapper());
    }

    @Override
    public List<MemberResponse> getListWithInfo() {
        String sql = """
                SELECT
                    m.`id` as `id`,
                    m.`username` as `username`,
                    m.`email` as `email`,
                    m.`password` as `password`,
                    m.`status` as `status`,
                    m.`created` as  `created`,
                    m.`modified` as  `modified`,
                    mi.`name` as `name`,
                    mi.`ip` as `ip`,
                    mi.`point` as `point`
                FROM `%s` m
                LEFT JOIN ``%s` mi
                    ON m.`id` = mi.`member_id`
                WHERE m.`status` = :status
                """.formatted(
                Table.Member,
                Table.MemberInfo
        );
        Map<String, Object> map = new HashMap<>();
        map.put("status", Status.ENABLED);
        return jdbcTemplate.query(sql, map, new MemberResponseRowMapper());
    }

    @Override
    public Integer create(MemberRequest request) {
        String sql = """
                INSERT INTO %s (
                    `username`,
                    `email`,
                    `password`,
                    `status`,
                    `created`
                ) VALUES (
                    :username,
                    :email,
                    :password,
                    :status,
                    :created
                )
                """.formatted(Table.Member);
        Map<String, Object> map = new HashMap<>();
        map.put("username", request.getUsername());
        map.put("email", request.getEmail());
        map.put("password", request.getPassword());
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
    public void update(Integer id, MemberRequest request) {
        String sql = """
                UPDATE %s SET
                `username`  = :username,
                `email`  = :email,
                `password` = :password,
                `status` = :status,
                `modified` = :modified
                WHERE id = :id
                """.formatted(Table.Member);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("username", request.getUsername());
        map.put("email", request.getEmail());
        map.put("password", request.getPassword());
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
                """.formatted(Table.Member);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", Status.DELETED);
        map.put("deleted", new Date());
        jdbcTemplate.update(sql, map);
    }
}
