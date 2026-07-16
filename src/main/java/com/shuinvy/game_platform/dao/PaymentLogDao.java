package com.shuinvy.game_platform.dao;

import com.shuinvy.game_platform.dto.PaymentLogRequest;
import com.shuinvy.game_platform.model.PaymentLog;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentLogDao {

    PaymentLog getById(Integer id);

    PaymentLog getByMemberGameId(Integer memberId, Integer gameId);

    List<PaymentLog> getList();

    Integer create(PaymentLogRequest request, String memberName, String gameName,
               BigDecimal point, Integer status);

    void delete(Integer id);
}
