package com.shuinvy.game_platform.controller;

import com.shuinvy.game_platform.aspect.Logging;
import com.shuinvy.game_platform.constant.Common;
import com.shuinvy.game_platform.dto.ApiResult;
import com.shuinvy.game_platform.dto.UserRequest;
import com.shuinvy.game_platform.dto.UserResponse;
import com.shuinvy.game_platform.model.User;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "User Controller", description = "後臺使用者相關的 API")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "取得後台人員資訊", description = "根據 id 取得指定後台人員資訊。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功回傳後台人員資訊"),
            @ApiResponse(
                    responseCode = "404",
                    description = "找不到該後台人員資訊",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )),
    })
    @GetMapping(value = "/users/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<User>> getUser(
            @PathVariable  Integer userId) {
        User user = userService.getById(userId);
        if  (user != null) {
            user.setPassword(Common.HashPassword);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResult<>(
                    Common.SUCCESS,
                    Common.SuccessMessage,
                    user
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResult<>("The user does not exist"));
    }

    @Operation(summary = "取得後台人員列表", description = "取得所有後台人員資訊，並以陣列回傳。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功回傳後台人員列表"),
    })
    @GetMapping(value = "/users",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<List<UserResponse>>> getList() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(Common.SUCCESS,
                Common.SuccessMessage,
                userService.getList()));
    }

    @Operation(summary = "新增後台人員資訊", description = "新增一名後台操作人員的帳號等資訊。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳新增的後台人員資訊"),
            @ApiResponse(
                    responseCode = "400",
                    description = "參數異常",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    ))
    })
    @PostMapping(value = "/users",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<UserResponse>> create(
            @Validated(UserRequest.OnCreate.class) @RequestBody UserRequest userRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>(errorMsg));
        }
        if (userService.checkExists(userRequest.getUsername(), 0)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The username is already exists"));
        }
        Integer newId = userService.create(userRequest);
        if (newId == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("Cannot create user"));
        }
        User user = userService.getById(newId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                new UserResponse(user)
        ));
    }

    @Operation(summary = "修改後台人員資訊", description = "修改一名指定後台操作人員的資訊。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳修改後的後台人員資訊"),
            @ApiResponse(
                    responseCode = "400",
                    description = "參數異常",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "找不到該後台人員資訊",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    ))
    })
    @PutMapping(value = "/users/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<UserResponse>> update(
             @PathVariable Integer userId,
             @Validated(UserRequest.OnUpdate.class) @RequestBody UserRequest userRequest,
             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>(errorMsg));
        }
        if (userService.getById(userId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResult<>("The user doesn't exist"));
        }
        if (userService.checkExists(userRequest.getUsername(), userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The username is already exists"));
        }
        userService.update(userId, userRequest);
        User updatedUser = userService.getById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                new UserResponse(updatedUser)
        ));
    }

    @Operation(summary = "刪除後台人員資訊", description = "刪除一名指定後台操作人員的資訊。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功"),
    })
    @DeleteMapping(value = "/users/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<Object>> delete(
            @PathVariable Integer userId) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                null
        ));
    }
}
