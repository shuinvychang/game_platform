package com.shuinvy.game_platform.rowmapper;

import com.shuinvy.game_platform.model.Notification;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationRowMapper implements RowMapper<Notification> {

    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
        Notification notification = new Notification();
        notification.setId(rs.getInt("id"));
        notification.setMemberId(rs.getInt("member_id"));
        notification.setIsNewGame(rs.getInt("is_new_game"));
        notification.setCreated(rs.getTimestamp("created"));
        notification.setModified(rs.getTimestamp("modified"));
        return notification;
    }
}
