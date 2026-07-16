package com.shuinvy.game_platform.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public class OperationLog {

    @Schema(description = "操作紀錄Id", example = "1")
    private Integer id;

    @Schema(description = "後台人員Id", example = "1")
    private Integer userId;

    @Schema(description = "後台人員帳號", example = "admin")
    private String username;

    @Schema(description = "操作類型，1: Post(新增); 2: Put(修改); 3: Delete(刪除)", example = "1")
    private Integer type;

    @Schema(description = "頁面網址路徑", example = "/user")
    private String path;

    @Schema(description = "請求參數，JSON格式")
    private String parameter;

    @Schema(description = "回傳結果，JSON格式")
    private String result;

    @Schema(description = "備註", example = "Test")
    private String memo;

    @Schema(description = "新增日期，時區: UTC+8", example = "2026-07-15 00:00:00")
    private Date created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
