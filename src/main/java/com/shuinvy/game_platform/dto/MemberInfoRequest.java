package com.shuinvy.game_platform.dto;

import java.math.BigDecimal;

public class MemberInfoRequest extends BaseRequest {

    private String name;
    private BigDecimal point;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }
}
