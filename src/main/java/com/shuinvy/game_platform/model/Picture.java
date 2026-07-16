package com.shuinvy.game_platform.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public class Picture {

    @Schema(description = "圖片Id", example = "1")
    private Integer id;

    @Schema(description = "圖片路徑或資源位置", example = "picture.png")
    private String path;

    @Schema(description = "參考Id", example = "1")
    private Integer referenceId;

    @Schema(description = "參考類型，代表圖片/資源使用在什麼功能", example = "Game")
    private String referenceType;

    @Schema(description = "資源類型", example = "image/png")
    private String contentType;

    @Schema(description = "狀態，1: 正常; 0: 刪除", example = "1")
    private Integer status;

    @Schema(description = "新增日期，時區: UTC+8", example = "2026-07-15 00:00:00")
    private Date created;

    @Schema(description = "修改日期，時區: UTC+8", example = "2026-07-15 00:00:00")
    private Date modified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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
}
