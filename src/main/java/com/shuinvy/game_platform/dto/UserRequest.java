package com.shuinvy.game_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest extends BaseRequest {

    public interface OnCreate {}
    public interface OnUpdate {}

    @Schema(description = "使用者名稱，即後台登入帳號", example = "admin")
    @NotBlank(message = "The username field cannot be empty", groups = OnCreate.class)
    @Size(max = 10, message = "The username field should be less than 10 characters", groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @Schema(description = "密碼，即後台登入密碼", example = "password")
    @NotBlank(message = "The password field cannot be empty", groups = OnCreate.class)
    @Size(max = 13, message = "The password field should be less than 13 characters", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @Schema(description = "角色Id，用來設定該使用者代表什麼角色，影響後台使用權限", example = "1")
    private Integer roleId;

    @Schema(description = "帳號狀態，1: 正常; 0: 停用", example = "1")
    private Integer status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
