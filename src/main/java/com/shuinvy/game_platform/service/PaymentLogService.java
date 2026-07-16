package com.shuinvy.game_platform.service;

import com.shuinvy.game_platform.dto.PaymentLogRequest;
import com.shuinvy.game_platform.model.PaymentLog;

import java.util.List;

public interface PaymentLogService {

    PaymentLog getById(Integer id);

    boolean checkExist(Integer memberId, Integer gameId);

    List<PaymentLog> getList();

    Integer create(PaymentLogRequest request);
}
