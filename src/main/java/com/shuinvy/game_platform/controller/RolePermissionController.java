package com.shuinvy.game_platform.controller;

import com.shuinvy.game_platform.aspect.Logging;
import com.shuinvy.game_platform.constant.Common;
import com.shuinvy.game_platform.dto.ApiResult;
import com.shuinvy.game_platform.dto.RolePermissionRequest;
import com.shuinvy.game_platform.dto.RolePermitResponse;
import com.shuinvy.game_platform.model.RolePermission;
import com.shuinvy.game_platform.model.User;
import com.shuinvy.game_platform.service.PermissionService;
import com.shuinvy.game_platform.service.RolePermitService;
import com.shuinvy.game_platform.service.RoleService;
import com.shuinvy.game_platform.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Role Permission Controller", description = "角色與權限配對相關的 API")
public class RolePermissionController {

    @Autowired
    private UserService userService;

    @Autowired
    private RolePermitService  rolePermitService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService  permitService;

    @Operation(summary = "取得目前後台人員的權限資訊", description = "根據目前後台人員所屬角色取得權限資訊的列表。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳目前後台人員擁有的權限列表"),
            @ApiResponse(
                    responseCode = "400",
                    description = "參數異常",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )),
            @ApiResponse(
                    responseCode = "401",
                    description = "尚未登入",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    ))
    })
    @GetMapping(value = "/userRolePermission",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<List<RolePermitResponse>>> getPermissionByUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResult<>("Please login first"));
        }
        User adminUser =
                (User) authentication.getPrincipal();
        if (adminUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResult<>("Please login first"));
        }
        Integer userId = adminUser.getId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResult<>("Please login first"));
        }
        if (userService.getById(userId) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResult<>("The user doesn't exist"));
        }
        User user =  userService.getById(userId);
        Integer roleId = user.getRoleId();
        if (roleId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The role doesn't exist"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(Common.SUCCESS,
                Common.SuccessMessage,
                rolePermitService.getListByRoleId(roleId)));
    }

    @Operation(summary = "取得角色與權限配對資訊", description = "取得指定的角色與權限配對的設定資訊。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳指定角色與權限設定"),
            @ApiResponse(
                    responseCode = "404",
                    description = "找不到該設定",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    ))
    })
    @GetMapping(value = "/rolePermission/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<RolePermitResponse>> getRolePermission(
            @PathVariable Integer id) {
        RolePermitResponse info = rolePermitService.getById(id);
        if (info == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResult<>("The configuration does not exist"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                info));
    }

    @Operation(summary = "取得所有角色與權限配對列表", description = "取得每個角色所擁有的權限的列表，並以陣列回傳。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳所有角色與權限設定"),
    })
    @GetMapping(value = "/rolePermission",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<List<RolePermitResponse>>> getList() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(Common.SUCCESS,
                Common.SuccessMessage,
                rolePermitService.getList()));
    }

    @Operation(summary = "新增指定角色的權限設定", description = "追加指定角色所擁有的權限設定。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳新增的設定資訊"),
            @ApiResponse(
                    responseCode = "400",
                    description = "參數異常",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )),
    })
    @PostMapping(value = "/rolePermission",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<RolePermitResponse>> create(
            @RequestBody RolePermissionRequest request){
        if (request.getRoleId() == null ||
            request.getPermissionId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The required field cannot be empty"));
        }
        if (roleService.getById(request.getRoleId()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The role doesn't exist"));
        }
        if (permitService.getById(request.getPermissionId()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The permission doesn't exist"));
        }
        Integer newId = 0;
        RolePermission obj = rolePermitService.getExists(request.getRoleId(), request.getPermissionId());
        if (obj != null) {
            newId  = obj.getId();
            rolePermitService.update(newId, request);
        } else {
            newId = rolePermitService.create(request);
            if (newId == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResult<>("Cannot create configuration"));
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                rolePermitService.getById(newId)));
    }
}
