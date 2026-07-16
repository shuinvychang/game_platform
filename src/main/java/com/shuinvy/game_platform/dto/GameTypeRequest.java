package com.shuinvy.game_platform.dto;

public class GameTypeRequest extends BaseRequest {

    private String name;
    private String code;
    private Integer status;

    public String  getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
