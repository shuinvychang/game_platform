package com.shuinvy.game_platform.service;

import com.shuinvy.game_platform.dto.OperationLogRequest;
import com.shuinvy.game_platform.model.OperationLog;

import java.util.List;

public interface OperationLogService {

    List<OperationLog> getList();

    Integer create(OperationLogRequest request);
}
