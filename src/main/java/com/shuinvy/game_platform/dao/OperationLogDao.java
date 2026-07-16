package com.shuinvy.game_platform.dao;

import com.shuinvy.game_platform.model.OperationLog;

import java.util.List;

public interface OperationLogDao {

    List<OperationLog> getList();

    Integer create(Integer userId, String username,
               Integer type, String path,
               String parameter, String result, String memo);
}
