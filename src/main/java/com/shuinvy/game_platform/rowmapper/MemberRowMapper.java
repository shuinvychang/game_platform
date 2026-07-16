package com.shuinvy.game_platform.rowmapper;

import com.shuinvy.game_platform.model.Member;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper implements RowMapper<Member> {

    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = new Member();
        member.setId(rs.getInt("id"));
        member.setUsername(rs.getString("username"));
        member.setEmail(rs.getString("email"));
        member.setPassword(rs.getString("password"));
        member.setStatus(rs.getInt("status"));
        member.setCreated(rs.getTimestamp("created"));
        member.setModified(rs.getTimestamp("modified"));
        return member;
    }
}
