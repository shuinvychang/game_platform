package com.shuinvy.game_platform.rowmapper;

import com.shuinvy.game_platform.model.GameType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameTypeRowMapper implements RowMapper<GameType> {

    @Override
    public GameType mapRow(ResultSet rs, int rowNum) throws SQLException {
        GameType gameType = new GameType();
        gameType.setId(rs.getInt("id"));
        gameType.setName(rs.getString("name"));
        gameType.setCode(rs.getString("code"));
        gameType.setStatus(rs.getInt("status"));
        gameType.setCreated(rs.getTimestamp("created"));
        gameType.setModified(rs.getTimestamp("modified"));
        return gameType;
    }
}
