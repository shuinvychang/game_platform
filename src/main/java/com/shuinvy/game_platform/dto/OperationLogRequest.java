package com.shuinvy.game_platform.dto;

public class OperationLogRequest {

    private Integer userId;

    private String username;

    private Integer type;

    private String path;

    private String parameter;

    private String result;

    private String memo;

    public OperationLogRequest(
            Integer userId,
            String username,
            Integer type,
            String path,
            String result) {
        this.userId = userId;
        this.username = username;
        this.type = type;
        this.path = path;
        this.result = result;
    }

    public OperationLogRequest(
            Integer userId,
            String username,
            Integer type,
            String path,
            String parameter,
            String result) {
        this.userId = userId;
        this.username = username;
        this.type = type;
        this.path = path;
        this.parameter = parameter;
        this.result = result;
    }

    public OperationLogRequest(
            Integer userId,
            String username,
            Integer type,
            String path,
            String parameter,
            String result,
            String memo) {
        this.userId = userId;
        this.username = username;
        this.type = type;
        this.path = path;
        this.parameter = parameter;
        this.result = result;
        this.memo = memo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
