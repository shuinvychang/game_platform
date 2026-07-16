package com.shuinvy.game_platform.service;

import com.shuinvy.game_platform.dto.NotificationRequest;
import com.shuinvy.game_platform.dto.NotificationResponse;
import com.shuinvy.game_platform.model.Notification;

import java.util.List;

public interface NotificationService {

    NotificationResponse getById(Integer id);

    Notification getByMemberId(Integer memberId);

    List<NotificationResponse> getList();

    Integer create(NotificationRequest request);

    void update(Integer id, NotificationRequest request);

    void delete(Integer id);

    void sendNotificationByType(String type, Integer referenceId);
}
