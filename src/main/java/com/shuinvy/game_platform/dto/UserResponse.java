package com.shuinvy.game_platform.dto;

import com.shuinvy.game_platform.constant.Common;
import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.Objects;

public class UserResponse {

    @Schema(description = "後台人員Id", example = "1")
    private final Integer id;

    @Schema(description = "後台人員登入帳號", example = "admin")
    private final String username;

    @Schema(description = "後台人員登入密碼", example = "password")
    private final String password;

    @Schema(description = "角色Id，影響後台操作權限", example = "1")
    private final Integer roleId;

    @Schema(description = "狀態，1: 正常; 0: 停用", example = "1")
    private final boolean status;

    @Schema(description = "註冊日期，時區: UTC+8", example = "2026-07-15 00:00:00")
    private final Date registrationDate;

    @Schema(description = "修改日期，時區: UTC+8", example = "2026-07-15 00:00:00")
    private final Date modified;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = Common.HashPassword;
        this.roleId = user.getRoleId();
        this.status = Objects.equals(user.getStatus(), Status.ENABLED);
        this.registrationDate = user.getCreated();
        this.modified = user.getModified();
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public boolean isStatus() {
        return status;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public Date getModified() {
        return modified;
    }
}
