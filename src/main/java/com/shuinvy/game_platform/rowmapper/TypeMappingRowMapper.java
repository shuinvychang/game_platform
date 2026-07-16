package com.shuinvy.game_platform.rowmapper;

import com.shuinvy.game_platform.model.TypeMapping;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeMappingRowMapper implements RowMapper<TypeMapping> {

    @Override
    public TypeMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
        TypeMapping typeMapping = new TypeMapping();
        typeMapping.setId(rs.getInt("id"));
        typeMapping.setGameId(rs.getInt("game_id"));
        typeMapping.setTypeId(rs.getInt("type_id"));
        typeMapping.setStatus(rs.getInt("status"));
        typeMapping.setCreated(rs.getTimestamp("created"));
        typeMapping.setModified(rs.getTimestamp("modified"));
        return typeMapping;
    }
}
