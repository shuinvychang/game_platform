package com.shuinvy.game_platform.service.impl;

import com.shuinvy.game_platform.common.EmailTemplateHandler;
import com.shuinvy.game_platform.constant.NotifyType;
import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.dao.*;
import com.shuinvy.game_platform.dto.NotificationLogRequest;
import com.shuinvy.game_platform.dto.NotificationRequest;
import com.shuinvy.game_platform.dto.NotificationResponse;
import com.shuinvy.game_platform.model.*;
import com.shuinvy.game_platform.service.NotificationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private MemberInfoDao memberInfoDao;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private NotificationLogDao logDao;

    @Autowired
    private OperationLogDao operationLogDao;

    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private EmailTemplateHandler emailTemplateHandler;

    @Override
    public NotificationResponse getById(Integer id) {
        Notification obj = notificationDao.getById(id);
        Member member = memberDao.getById(obj.getMemberId());
        MemberInfo memberInfo = memberInfoDao.getByMemberId(obj.getMemberId());
        return new NotificationResponse(obj, member, memberInfo);
    }

    @Override
    public Notification getByMemberId(Integer memberId) {
        return notificationDao.getByMemberId(memberId);
    }

    @Override
    public List<NotificationResponse> getList() {
        List<NotificationResponse> list = notificationDao.getListWithMember();
        Map<Integer, MemberInfo> map = getMemberInfoMapper();
        List<NotificationResponse> result = new ArrayList<>();
        Integer memberId;
        MemberInfo memberInfo;
        // Try to fill the member_name field
        for (NotificationResponse obj : list) {
            memberId = obj.getMemberId();
            if (map.containsKey(memberId)) {
                memberInfo = map.get(memberId);
                obj.setMemberName(memberInfo.getName());
                result.add(obj);
            }
        }
        return result;
    }

    @Override
    public Integer create(NotificationRequest request) {
        return notificationDao.create(request);
    }

    @Override
    public void update(Integer id, NotificationRequest request) {
        if (request.getIsNewGame() == null) {
            request.setIsNewGame(0);
        }
        notificationDao.update(id, request);
    }

    @Override
    public void delete(Integer id) {
        notificationDao.delete(id);
    }

    private Map<Integer, MemberInfo> getMemberInfoMapper() {
        Map<Integer, MemberInfo> map = new HashMap<>();
        List<MemberInfo> memberInfos = memberInfoDao.getList();
        for (MemberInfo memberInfo : memberInfos) {
            map.put(memberInfo.getMemberId(), memberInfo);
        }
        return map;
    }

    @Override
    public void sendNotificationByType(String type, Integer referenceId) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        User adminUser =
                (User) authentication.getPrincipal();
        Integer userId = adminUser.getId();
        String username = adminUser.getUsername();
        switch (type) {
            case NotifyType.New_Game:
                try {
                    sendNotificationForNewGame(referenceId);
                } catch (MessagingException e) {
                    operationLogDao.create(
                            userId,
                            username,
                            1,
                            "/send_notification",
                            "",
                            "Cannot send email",
                            "New_Game: %d".formatted(referenceId));
                }
                break;
            default:
        }
    }

    private void sendNotificationForNewGame(Integer referenceId)
            throws MessagingException {
        Game game =  gameDao.getById(referenceId);
        List<Picture> pictureList = pictureDao.getListByGameId(referenceId);
        Map<Integer, MemberInfo> memberInfoMap = getMemberInfoMapper();
        MemberInfo memberInfo;
        // Get notification of members
        List<NotificationResponse> config = notificationDao.getListByTypeRefId(
            NotifyType.New_Game);
        NotificationLogRequest log;
        String subject = "[Game Platform] New game is published";
        String html = emailTemplateHandler.getNewGameEmailTemplate(
                game.getName(),
                game.getName(),
                game.getInfo(),
                game.getDescription(),
                pictureList.size()
        );
        for (NotificationResponse obj : config) {
            if (!obj.getIsNewGame().equals(Status.ENABLED)) {
                continue;
            }
            emailTemplateHandler.sendHtmlMail(
                    obj.getEmail(),
                    subject,
                    html,
                    pictureList);
            // Save send notification to log
            log =  new NotificationLogRequest();
            log.setMemberId(obj.getMemberId());
            memberInfo = memberInfoMap.get(obj.getMemberId());
            log.setMemberName(memberInfo.getName());
            log.setNotifyType(NotifyType.New_Game);
            log.setEmail(obj.getEmail());
            log.setReferenceId(referenceId);
            log.setMemo(game.getName());
            logDao.create(log);
        }
    }

}
