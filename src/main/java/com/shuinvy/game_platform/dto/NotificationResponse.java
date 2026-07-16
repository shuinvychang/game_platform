package com.shuinvy.game_platform.dto;

import com.shuinvy.game_platform.model.Member;
import com.shuinvy.game_platform.model.MemberInfo;
import com.shuinvy.game_platform.model.Notification;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public class NotificationResponse {

    @Schema(description = "通知設定Id", example = "1")
    private Integer id;

    @Schema(description = "會員Id", example = "1")
    private Integer memberId;

    @Schema(description = "會員名稱", example = "Test")
    private String memberName;

    @Schema(description = "會員電子信箱", example = "test@example.com")
    private String email;

    @Schema(description = "是否通知新遊戲上架，1: 是; 0: 否", example = "1")
    private Integer isNewGame;

    @Schema(description = "新增日期，時區: UTC+8", example = "2026-07-15 00:00:00")
    private Date created;

    @Schema(description = "修改日期，時區: UTC+8", example = "2026-07-15 00:00:00")
    private Date modified;

    public NotificationResponse() {

    }

    public NotificationResponse(
            Notification info,
            Member member,
            MemberInfo memberInfo) {
        this.id = info.getId();
        this.memberId = member.getId();
        this.memberName = memberInfo.getName();
        this.email = member.getEmail();
        this.isNewGame = info.getIsNewGame();
        this.created = info.getCreated();
        this.modified = info.getModified();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsNewGame() {
        return isNewGame;
    }

    public void setIsNewGame(Integer isNewGame) {
        this.isNewGame = isNewGame;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}
