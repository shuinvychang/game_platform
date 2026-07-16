package com.shuinvy.game_platform.rowmapper;

import com.shuinvy.game_platform.model.Picture;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PictureRowMapper implements RowMapper<Picture> {

    @Override
    public Picture mapRow(ResultSet rs, int rowNum) throws SQLException {
        Picture picture = new Picture();
        picture.setId(rs.getInt("id"));
        picture.setPath(rs.getString("path"));
        picture.setReferenceId(rs.getInt("reference_id"));
        picture.setReferenceType(rs.getString("reference_type"));
        picture.setContentType(rs.getString("content_type"));
        picture.setStatus(rs.getInt("status"));
        picture.setCreated(rs.getTimestamp("created"));
        picture.setModified(rs.getTimestamp("modified"));
        return picture;
    }
}
