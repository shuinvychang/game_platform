package com.shuinvy.game_platform.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public class Permission {

    @Schema(description = "權限Id", example = "1")
    private Integer id;

    @Schema(description = "頁面代號", example = "member_page")
    private String page;

    @Schema(description = "按鈕代號", example = "update")
    private String button;

    @Schema(description = "狀態，1: 正常; 0: 刪除", example = "1")
    private Integer status;

    @Schema(description = "新增日期，時區: UTC+8", example = "2026-07-15 00:00:00")
    private Date created;

    @Schema(description = "修改日期，時區: UTC+8", example = "2026-07-15 00:00:00")
    private Date modified;

    @Schema(description = "刪除日期，時區: UTC+8", example = "2026-07-15 00:00:00")
    private Date deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
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

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }
}
