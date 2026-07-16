package com.shuinvy.game_platform.rowmapper;

import com.shuinvy.game_platform.dto.NotificationResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationResponseRowMapper implements RowMapper<NotificationResponse> {

    @Override
    public NotificationResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setId(rs.getInt("id"));
        notificationResponse.setMemberId(rs.getInt("member_id"));
        notificationResponse.setIsNewGame(rs.getInt("is_new_game"));
        notificationResponse.setEmail(rs.getString("email"));
        notificationResponse.setCreated(rs.getTimestamp("created"));
        notificationResponse.setModified(rs.getTimestamp("modified"));
        return notificationResponse;
    }
}
