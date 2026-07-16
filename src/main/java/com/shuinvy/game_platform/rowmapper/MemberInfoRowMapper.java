package com.shuinvy.game_platform.rowmapper;

import com.shuinvy.game_platform.model.MemberInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberInfoRowMapper implements RowMapper<MemberInfo> {

    @Override
    public MemberInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        MemberInfo member = new MemberInfo();
        member.setId(rs.getInt("id"));
        member.setMemberId(rs.getInt("member_id"));
        member.setName(rs.getString("name"));
        member.setIp(rs.getString("ip"));
        member.setPoint(rs.getBigDecimal("point"));
        member.setStatus(rs.getInt("status"));
        member.setCreated(rs.getTimestamp("created"));
        member.setModified(rs.getTimestamp("modified"));
        return member;
    }
}
