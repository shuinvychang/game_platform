package com.shuinvy.game_platform.rowmapper;

import com.shuinvy.game_platform.model.OperationLog;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperationLogRowMapper implements RowMapper<OperationLog> {

    @Override
    public OperationLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        OperationLog operationLog = new OperationLog();
        operationLog.setId(rs.getInt("id"));
        operationLog.setType(rs.getInt("type"));
        operationLog.setPath(rs.getString("path"));
        operationLog.setParameter(rs.getString("parameter"));
        operationLog.setResult(rs.getString("result"));
        operationLog.setMemo(rs.getString("memo"));
        operationLog.setCreated(rs.getTimestamp("created"));
        return operationLog;
    }
}
