package com.shuinvy.game_platform.service.impl;

import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.dao.GameDao;
import com.shuinvy.game_platform.dao.MemberInfoDao;
import com.shuinvy.game_platform.dao.PaymentLogDao;
import com.shuinvy.game_platform.dto.PaymentLogRequest;
import com.shuinvy.game_platform.model.Game;
import com.shuinvy.game_platform.model.MemberInfo;
import com.shuinvy.game_platform.model.PaymentLog;
import com.shuinvy.game_platform.service.PaymentLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentLogServiceImpl implements PaymentLogService {

    @Autowired
    private PaymentLogDao paymentLogDao;

    @Autowired
    private MemberInfoDao memberInfoDao;

    @Autowired
    private GameDao gameDao;

    @Override
    public PaymentLog getById(Integer id) {
        return paymentLogDao.getById(id);
    }

    @Override
    public boolean checkExist(Integer memberId, Integer gameId) {
        PaymentLog log = paymentLogDao.getByMemberGameId(memberId, gameId);
        return log != null;
    }

    @Override
    public List<PaymentLog> getList() {
        return paymentLogDao.getList();
    }

    @Override
    public Integer create(PaymentLogRequest request) {
        MemberInfo member = memberInfoDao.getByMemberId(request.getMemberId());
        Game game = gameDao.getById(request.getGameId());
        return paymentLogDao.create(
                request,
                member.getName(),
                game.getName(),
                game.getPrice(),
                Status.ENABLED);
    }
}
