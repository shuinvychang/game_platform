package com.shuinvy.game_platform.dto;

import com.shuinvy.game_platform.constant.Common;
import com.shuinvy.game_platform.model.Member;
import com.shuinvy.game_platform.model.MemberInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Date;

public class MemberResponse {

    @Schema(description = "會員Id", example = "1")
    private Integer id;

    @Schema(description = "會員帳號", example = "test")
    private String username;

    @Schema(description = "會員電子信箱", example = "test@example.com")
    private String email;

    @Schema(description = "會員密碼", example = "******")
    private String password;

    @Schema(description = "會員名稱或暱稱", example = "Nickname")
    private String name;

    @Schema(description = "會員註冊IP", example = "192.168.0.10")
    private String ip;

    @Schema(description = "會員可使用的點數，可用來購買遊戲", example = "0.0")
    private BigDecimal point;

    @Schema(description = "狀態，1: 正常; 0: 停用", example = "1")
    private Integer status;

    @Schema(description = "註冊日期，時區: UTC+8", example = "2026-07-15 00:00:00")
    private Date registrationDate;

    @Schema(description = "修改日期，時區: UTC+8", example = "2026-07-15 00:00:00")
    private Date modified;

    public MemberResponse(Member member, MemberInfo memberInfo) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.password = Common.HashPassword;
        this.name = memberInfo.getName();
        this.ip = memberInfo.getIp();
        this.point = memberInfo.getPoint();
        this.status = member.getStatus();
        this.registrationDate = member.getCreated();
        this.modified = member.getModified();
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public Integer getStatus() {
        return status;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public Date getModified() {
        return modified;
    }

}
