package com.shuinvy.game_platform.rowmapper;

import com.shuinvy.game_platform.model.Game;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameRowMapper implements RowMapper<Game> {

    @Override
    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
        Game game = new Game();
        game.setId(rs.getInt("id"));
        game.setName(rs.getString("name"));
        game.setInfo(rs.getString("info"));
        game.setDescription(rs.getString("description"));
        game.setPrice(rs.getBigDecimal("price"));
        game.setIsPublished(rs.getInt("is_published"));
        game.setPublished(rs.getTimestamp("published"));
        game.setStatus(rs.getInt("status"));
        game.setCreated(rs.getTimestamp("created"));
        game.setModified(rs.getTimestamp("modified"));
        return game;
    }
}
