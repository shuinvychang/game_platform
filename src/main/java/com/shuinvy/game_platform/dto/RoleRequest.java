package com.shuinvy.game_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoleRequest extends BaseRequest {

    public interface OnCreate {}
    public interface OnUpdate {}

    @Schema(description = "角色名稱", example = "admin")
    @NotBlank(message = "The name field cannot be empty", groups = {OnCreate.class, OnUpdate.class})
    @Size(max = 30, message = "The name field should be less than 30 characters", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Schema(description = "狀態，1: 啟用; 0: 刪除", example = "1")
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
