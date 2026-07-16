package com.shuinvy.game_platform.dao;

import com.shuinvy.game_platform.dto.NotificationLogRequest;

public interface NotificationLogDao {

    Integer create(NotificationLogRequest request);
}
