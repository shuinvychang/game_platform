package com.shuinvy.game_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginRequest extends BaseRequest {

    @Schema(description = "後台登入帳號", example = "admin")
    private String username;

    @Schema(description = "後台登入密碼", example = "password")
    private String password;

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
}
