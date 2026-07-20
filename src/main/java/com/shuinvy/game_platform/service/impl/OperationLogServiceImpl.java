package com.shuinvy.game_platform.service.impl;

import com.shuinvy.game_platform.dao.OperationLogDao;
import com.shuinvy.game_platform.dto.OperationLogRequest;
import com.shuinvy.game_platform.model.OperationLog;
import com.shuinvy.game_platform.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogDao operationLogDao;

    @Override
    public List<OperationLog> getList() {
        return operationLogDao.getList();
    }

    @Override
    public Integer create(OperationLogRequest request) {
        if (request.getParameter() == null || request.getParameter().isEmpty()) {
            request.setParameter("");
        }
        if (request.getMemo() == null || request.getMemo().isEmpty()) {
            request.setMemo("");
        }
        return operationLogDao.create(
                request.getUserId(),
                request.getUsername(),
                request.getType(),
                request.getPath(),
                request.getParameter(),
                request.getResult(),
                request.getMemo()
        );
    }
}
