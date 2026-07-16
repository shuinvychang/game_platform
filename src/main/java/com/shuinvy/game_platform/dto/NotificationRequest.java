package com.shuinvy.game_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class NotificationRequest extends BaseRequest {

    @Schema(description = "會員Id", example = "1")
    private Integer memberId;

    @Schema(description = "是否通知新遊戲上架，1: 是; 0: 否", example = "1")
    private Integer isNewGame;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getIsNewGame() {
        return isNewGame;
    }

    public void setIsNewGame(Integer isNewGame) {
        this.isNewGame = isNewGame;
    }
}
