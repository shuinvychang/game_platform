package com.shuinvy.game_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MemberRequest extends BaseRequest {

    public interface OnCreate {}
    public interface OnUpdate {}

    @Schema(description = "使用者名稱", example = "test")
    @NotBlank(message = "The username field cannot be empty", groups = OnCreate.class)
    @Size(max = 13, message = "The username field should be less than 13 characters", groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @Schema(description = "電子信箱，若有開啟通知設定則會寄信到此Email", example = "test@example.com")
    @NotBlank(message = "The email field cannot be empty", groups = OnCreate.class)
    @Size(max = 50, message = "The email field should be less than 50 characters", groups = {OnCreate.class, OnUpdate.class})
    @Email(message = "Email format is invalid", groups = {OnCreate.class, OnUpdate.class})
    private String email;

    @Schema(description = "密碼", example = "password")
    @NotBlank(message = "The password field cannot be empty", groups = OnCreate.class)
    @Size(max = 13, message = "The password field should be less than 13 characters", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @Schema(description = "會員名稱或暱稱", example = "Nickname")
    @NotBlank(message = "The name field cannot be empty", groups = OnCreate.class)
    @Size(max = 30, message = "The name field should be less than 30 characters", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Schema(description = "帳號狀態，1: 正常; 0: 停用", example = "1")
    private Integer status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
