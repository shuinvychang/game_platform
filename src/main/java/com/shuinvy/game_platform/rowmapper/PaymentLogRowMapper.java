package com.shuinvy.game_platform.rowmapper;

import com.shuinvy.game_platform.model.PaymentLog;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentLogRowMapper implements RowMapper<PaymentLog> {

    @Override
    public PaymentLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        PaymentLog paymentLog = new PaymentLog();
        paymentLog.setId(rs.getInt("id"));
        paymentLog.setMemberId(rs.getInt("member_id"));
        paymentLog.setMemberName(rs.getString("member_name"));
        paymentLog.setGameId(rs.getInt("game_id"));
        paymentLog.setGameName(rs.getString("game_name"));
        paymentLog.setPoint(rs.getBigDecimal("point"));
        paymentLog.setStatus(rs.getInt("status"));
        paymentLog.setCreated(rs.getTimestamp("created"));
        return paymentLog;
    }
}
