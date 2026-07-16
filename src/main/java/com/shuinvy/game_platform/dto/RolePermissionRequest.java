package com.shuinvy.game_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class RolePermissionRequest extends BaseRequest {

    @Schema(description = "角色Id", example = "1")
    private Integer roleId;

    @Schema(description = "權限Id", example = "1")
    private Integer permissionId;

    @Schema(description = "狀態，1:啟用; 0:刪除", example = "1")
    private Integer status;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
