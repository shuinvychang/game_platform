package com.shuinvy.game_platform.dto;

import com.shuinvy.game_platform.constant.Status;
import com.shuinvy.game_platform.model.Permission;
import com.shuinvy.game_platform.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;

public class RolePermitResponse {

    @Schema(description = "角色Id", example = "1")
    private Integer roleId;

    @Schema(description = "角色名稱", example = "admin")
    private String roleName;

    @Schema(description = "權限Id", example = "1")
    private Integer permitId;

    @Schema(description = "頁面代號", example = "member_page")
    private String page;

    @Schema(description = "按鈕代號", example = "update")
    private String button;

    @Schema(description = "是否有權限，true/false", example = "true")
    private boolean permission;

    public RolePermitResponse() {}

    public RolePermitResponse(Role role, Permission permit, Integer status) {
        this.roleId = role.getId();
        this.roleName = role.getName();
        this.permitId = permit.getId();
        this.page = permit.getPage();
        this.button = permit.getButton();
        this.permission = status.equals(Status.ENABLED);
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getPermitId() {
        return permitId;
    }

    public void setPermitId(Integer permitId) {
        this.permitId = permitId;
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

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }
}
