package com.shuinvy.game_platform.rowmapper;

import com.shuinvy.game_platform.dto.MemberResponse;
import com.shuinvy.game_platform.model.Member;
import com.shuinvy.game_platform.model.MemberInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberResponseRowMapper implements RowMapper<MemberResponse> {

    @Override
    public MemberResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = new Member();
        member.setId(rs.getInt("id"));
        member.setUsername(rs.getString("username"));
        member.setEmail(rs.getString("email"));
        member.setPassword(rs.getString("password"));
        member.setStatus(rs.getInt("status"));
        member.setCreated(rs.getTimestamp("created"));
        member.setModified(rs.getTimestamp("modified"));
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setName(rs.getString("name"));
        memberInfo.setIp(rs.getString("ip"));
        memberInfo.setPoint(rs.getBigDecimal("point"));
        return new MemberResponse(
                member,
                memberInfo
        );
    }
}
