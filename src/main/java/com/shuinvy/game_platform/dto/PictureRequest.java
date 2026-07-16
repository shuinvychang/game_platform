package com.shuinvy.game_platform.dto;

public class PictureRequest extends BaseRequest {

    private String path;
    private Integer referenceId;
    private String referenceType;
    private String contentType;
    private Integer status;

    public PictureRequest() {}

    public PictureRequest(String path, String contentType) {
        this.path = path;
        this.contentType = contentType;
        this.status = 1;
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
}
