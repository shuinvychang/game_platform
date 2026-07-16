package com.shuinvy.game_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PermissionRequest extends BaseRequest {

    public interface OnCreate {}
    public interface OnUpdate {}

    @Schema(description = "頁面代號", example = "member_page")
    @NotBlank(message = "The page field cannot be empty", groups = OnCreate.class)
    @Size(max = 50, message = "The page field should be less than 50 characters", groups = {OnCreate.class, OnUpdate.class})
    private String page;

    @Schema(description = "按鈕代號", example = "update")
    @Size(max = 50, message = "The button field should be less than 50 characters", groups = {OnCreate.class, OnUpdate.class})
    private String button;

    @Schema(description = "狀態，1: 正常; 0: 刪除", example = "1")
    private Integer status;

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
}
