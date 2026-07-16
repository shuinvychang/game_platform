package com.shuinvy.game_platform.controller;

import com.shuinvy.game_platform.aspect.Logging;
import com.shuinvy.game_platform.constant.Common;
import com.shuinvy.game_platform.dto.ApiResult;
import com.shuinvy.game_platform.dto.PermissionRequest;
import com.shuinvy.game_platform.model.Permission;
import com.shuinvy.game_platform.service.PermissionService;
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
@Tag(name = "Permission Controller", description = "後臺使用權限相關的 API")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Operation(summary = "取得權限資訊", description = "取得指定的後台人員權限資訊。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳指定的權限資訊"),
            @ApiResponse(
                    responseCode = "404",
                    description = "找不到該權限設定",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    ))
    })
    @GetMapping(value = "/permits/{permitId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<Permission>> getPermission(
            @PathVariable Integer permitId) {
        Permission obj = permissionService.getById(permitId);
        if (obj != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResult<>(
                    Common.SUCCESS,
                    Common.SuccessMessage,
                    obj
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResult<>("The configuration does not exist"));
    }

    @Operation(summary = "取得權限列表", description = "取得所有後台人員權限設定的列表。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳所有的權限設定列表"),
    })
    @GetMapping(value = "/permits",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<List<Permission>>> getList() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(Common.SUCCESS,
                Common.SuccessMessage,
                permissionService.getList()));
    }

    @Operation(summary = "修改權限", description = "修改指定的後台人員權限的設定。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳新增的權限設定"),
            @ApiResponse(
                    responseCode = "400",
                    description = "參數異常",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )),
    })
    @PostMapping(value = "/permits",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<Permission>> create(
            @Validated(PermissionRequest.OnCreate.class) @RequestBody PermissionRequest obj,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>(errorMsg));
        }
        String button = "";
        if (obj.getButton() != null) {
            button = obj.getButton();
        }
        if (permissionService.checkExists(obj.getPage(), button, 0)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The configuration is already exists"));
        }
        Integer newId = permissionService.create(obj);
        if (newId == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("Cannot create permission"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                permissionService.getById(newId)));
    }

    @Operation(summary = "修改權限", description = "修改指定的後台人員權限的設定。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳修改後的權限資訊"),
            @ApiResponse(
                    responseCode = "400",
                    description = "參數異常",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "找不到該權限資訊",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    ))
    })
    @PutMapping(value = "/permits/{permitId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<Permission>> update(
            @PathVariable Integer permitId,
            @Validated(PermissionRequest.OnUpdate.class) @RequestBody PermissionRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>(errorMsg));
        }
        Permission obj = permissionService.getById(permitId);
        if (obj == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResult<>("The configuration doesn't exist"));
        }
        String button = "";
        if (request.getButton() != null) {
            button = request.getButton();
        }
        if (permissionService.checkExists(request.getPage(), button, permitId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The configuration is already exists"));
        }
        permissionService.update(permitId, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                permissionService.getById(permitId)));
    }

    @Operation(summary = "刪除權限", description = "刪除指定的後台人員權限的設定。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功"),
    })
    @DeleteMapping(value = "/permits/{permitId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<Object>> delete(
            @PathVariable Integer permitId) {
        permissionService.delete(permitId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                null
        ));
    }

}
