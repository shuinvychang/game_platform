package com.shuinvy.game_platform.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentLog {

    @Schema(description = "消費紀錄Id", example = "1")
    private Integer id;

    @Schema(description = "會員Id", example = "1")
    private Integer memberId;

    @Schema(description = "會員名稱", example = "Test")
    private String memberName;

    @Schema(description = "遊戲Id", example = "1")
    private Integer gameId;

    @Schema(description = "遊戲名稱", example = "Game")
    private String gameName;

    @Schema(description = "消費金額/點數", example = "10.5")
    private BigDecimal point;

    @Schema(description = "狀態，用來區分測試資料。1: 一般會員資料; 0: 測試資料", example = "1")
    private Integer status;

    @Schema(description = "新增日期，時區: UTC+8", example = "2026-07-15 00:00:00")
    private Date created;

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

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
