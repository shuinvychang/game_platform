package com.shuinvy.game_platform.controller;

import com.shuinvy.game_platform.aspect.Logging;
import com.shuinvy.game_platform.constant.Common;
import com.shuinvy.game_platform.dto.ApiResult;
import com.shuinvy.game_platform.dto.NotificationRequest;
import com.shuinvy.game_platform.dto.NotificationResponse;
import com.shuinvy.game_platform.model.Notification;
import com.shuinvy.game_platform.service.MemberService;
import com.shuinvy.game_platform.service.NotificationService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Notification Controller", description = "會員通知設定相關的 API")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MemberService memberService;

    @Operation(summary = "取得通知設定資訊", description = "根據會員Id取得該會員的所有通知設定")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳該會員的通知設定"),
            @ApiResponse(
                    responseCode = "404",
                    description = "找不到該會員的通知設定",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    ))
    })
    @GetMapping(value = "/notifications/{memberId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<NotificationResponse>> getNotification(
            @PathVariable Integer memberId) {
        Notification obj = notificationService.getByMemberId(memberId);
        if (obj == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResult<>("The configuration does not exist"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                notificationService.getById(obj.getId())));
    }

    @Operation(summary = "取得通知設定列表", description = "取得所有會員的通知設定列表。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳所有會員的通知設定列表"),
    })
    @GetMapping(value = "/notifications",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<List<NotificationResponse>>> getList() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(Common.SUCCESS,
                Common.SuccessMessage,
                notificationService.getList()));
    }

    @Operation(summary = "新增通知設定", description = "新增指定會員的通知設定。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳新增的通知設定"),
            @ApiResponse(
                    responseCode = "400",
                    description = "參數異常",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )),
    })
    @PostMapping(value = "/notifications",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<NotificationResponse>> create(
            @RequestBody NotificationRequest request) {
        if (request.getMemberId() == null
        || request.getIsNewGame() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The required field cannot be empty"));
        }
        Integer newId = 0;
        // Check if there is member configuration
        Notification obj = notificationService.getByMemberId(request.getMemberId());
        if (memberService.getById(request.getMemberId()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The member doesn't exist"));
        }
        if (obj != null) {
            newId =  obj.getId();
            notificationService.update(newId, request);
        } else {
            newId = notificationService.create(request);
        }
        if (newId == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("Cannot create configuration"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                notificationService.getById(newId)));
    }

    @Operation(summary = "修改通知設定", description = "修改指定會員的通知設定，將值設為0則相當於刪除設定。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳修改後的通知設定"),
            @ApiResponse(
                    responseCode = "400",
                    description = "參數異常",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "找不到該通知設定資訊",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    ))
    })
    @PutMapping(value = "/notifications/{memberId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<NotificationResponse>> update(
            @PathVariable Integer memberId,
            @RequestBody NotificationRequest request) {
        if (memberService.getById(memberId) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The member doesn't exist"));
        }
        Notification obj = notificationService.getByMemberId(memberId);
        if (obj == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResult<>("The configuration doesn't exist"));
        }
        Integer id = obj.getId();
        request.setMemberId(memberId);
        notificationService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                notificationService.getById(id)));
    }

}
