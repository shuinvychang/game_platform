package com.shuinvy.game_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class PaymentLogRequest extends BaseRequest {

    @Schema(description = "購買遊戲的會員Id", example = "1")
    private Integer memberId;

    @Schema(description = "遊戲Id", example = "1")
    private Integer gameId;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }
}
