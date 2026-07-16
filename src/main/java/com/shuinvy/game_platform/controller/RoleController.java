package com.shuinvy.game_platform.controller;

import com.shuinvy.game_platform.aspect.Logging;
import com.shuinvy.game_platform.constant.Common;
import com.shuinvy.game_platform.dto.ApiResult;
import com.shuinvy.game_platform.dto.RoleRequest;
import com.shuinvy.game_platform.model.Role;
import com.shuinvy.game_platform.service.RoleService;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Role Controller", description = "後臺使用者角色相關的 API")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Operation(summary = "取得角色設定列表", description = "取得後台人員的角色設定列表。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳角色列表"),
    })
    @GetMapping(value = "/roles",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<List<Role>>> getList() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                roleService.getList()
        ));
    }

    @Operation(summary = "新增角色", description = "新增後台人員的角色設定。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳新增的角色資訊"),
            @ApiResponse(
                    responseCode = "400",
                    description = "參數異常",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )),
    })
    @PostMapping(value = "/roles",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<Role>> create(
            @Validated(RoleRequest.OnCreate.class) @RequestBody RoleRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>(errorMsg));
        }
        if (roleService.checkExists(request.getName(), 0)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The name is already exists"));
        }
        Integer newId = roleService.create(request);
        if  (newId == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("Cannot create role"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                roleService.getById(newId)
        ));
    }

    @Operation(summary = "修改角色", description = "修改指定後台人員的角色設定。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳修改後的角色資訊"),
            @ApiResponse(
                    responseCode = "400",
                    description = "參數異常",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "找不到該角色資訊",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    ))
    })
    @PutMapping(value = "/roles/{roleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<Role>> update(
            @PathVariable Integer roleId,
            @Validated(RoleRequest.OnUpdate.class) @RequestBody RoleRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>(errorMsg));
        }
        if (roleService.getById(roleId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResult<>("The role doesn't exist"));
        }
        if (roleService.checkExists(request.getName(), roleId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The name is already exists"));
        }
        roleService.update(roleId, request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                roleService.getById(roleId)
        ));
    }

    @Operation(summary = "刪除角色", description = "刪除指定的後台人員的角色設定。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功"),
    })
    @DeleteMapping(value = "/roles/{roleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<Object>> delete(
            @PathVariable Integer roleId) {
        roleService.delete(roleId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                null
        ));
    }
}
