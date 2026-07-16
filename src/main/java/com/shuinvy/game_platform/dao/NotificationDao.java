package com.shuinvy.game_platform.dao;

import com.shuinvy.game_platform.dto.NotificationRequest;
import com.shuinvy.game_platform.dto.NotificationResponse;
import com.shuinvy.game_platform.model.Notification;

import java.util.List;

public interface NotificationDao {

    Notification getById(Integer id);

    Notification getByMemberId(Integer memberId);

    List<Notification> getList();

    List<NotificationResponse> getListByTypeRefId(String type);

    List<NotificationResponse> getListWithMember();

    Integer create(NotificationRequest request);

    void update(Integer id, NotificationRequest request);

    void delete(Integer id);
}
