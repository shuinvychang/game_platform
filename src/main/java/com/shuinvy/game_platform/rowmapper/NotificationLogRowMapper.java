package com.shuinvy.game_platform.rowmapper;

import com.shuinvy.game_platform.model.NotificationLog;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationLogRowMapper implements RowMapper<NotificationLog> {

    @Override
    public NotificationLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        NotificationLog notificationLog = new NotificationLog();
        notificationLog.setId(rs.getInt("id"));
        notificationLog.setMemberId(rs.getInt("member_id"));
        notificationLog.setMemberName(rs.getString("member_name"));
        notificationLog.setNotifyType(rs.getString("notify_type"));
        notificationLog.setEmail(rs.getString("email"));
        notificationLog.setReferenceId(rs.getInt("reference_id"));
        notificationLog.setMemo(rs.getString("memo"));
        notificationLog.setCreated(rs.getTimestamp("created"));
        return notificationLog;
    }
}
