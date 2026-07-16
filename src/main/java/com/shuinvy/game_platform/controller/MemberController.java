package com.shuinvy.game_platform.controller;

import com.shuinvy.game_platform.aspect.Logging;
import com.shuinvy.game_platform.common.IPHandler;
import com.shuinvy.game_platform.constant.Common;
import com.shuinvy.game_platform.dto.ApiResult;
import com.shuinvy.game_platform.dto.MemberRequest;
import com.shuinvy.game_platform.dto.MemberResponse;
import com.shuinvy.game_platform.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Member Controller", description = "平台會員相關的 API")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Operation(summary = "取得會員資訊", description = "取得指定會員的帳號與個人資訊。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳該會員資訊"),
            @ApiResponse(
                    responseCode = "404",
                    description = "找不到該會員資訊",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    ))
    })
    @GetMapping(value = "/members/{memberId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<MemberResponse>> getMember(
            @PathVariable Integer memberId) {
        MemberResponse member = memberService.getById(memberId);
        if (member != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResult<>(
                    Common.SUCCESS,
                    Common.SuccessMessage,
                    member));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResult<>("The member does not exist"));
    }

    @Operation(summary = "取得會員列表", description = "取得所有會員的帳號與個人資訊的列表。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳會員列表"),
    })
    @GetMapping(value = "/members",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<List<MemberResponse>>> getList() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(Common.SUCCESS,
                Common.SuccessMessage,
                memberService.getList()));
    }

    @Operation(summary = "新增會員", description = "新增一名會員的帳號與個人資訊。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳新增的會員資訊"),
            @ApiResponse(
                    responseCode = "400",
                    description = "參數異常",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )),
    })
    @PostMapping(value = "/members",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<MemberResponse>> create(
            @Validated(MemberRequest.OnCreate.class) @RequestBody MemberRequest memberRequest,
            BindingResult bindingResult,
            HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>(errorMsg));
        }
        if (memberService.checkExists(memberRequest.getUsername(), 0)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The username is already exists"));
        }
        String ip = IPHandler.getClientIp(httpServletRequest);
        Integer newId = memberService.create(memberRequest, ip);
        if (newId == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("Cannot create member"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                memberService.getById(newId)));
    }

    @Operation(summary = "修改會員", description = "修改一名會員的帳號與個人資訊。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳修改後的會員資訊"),
            @ApiResponse(
                    responseCode = "400",
                    description = "參數異常",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "找不到該會員資訊",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    ))
    })
    @PutMapping(value = "/members/{memberId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<MemberResponse>> update(
            @PathVariable Integer memberId,
            @Validated(MemberRequest.OnUpdate.class) @RequestBody MemberRequest memberRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>(errorMsg));
        }
        if (memberService.getById(memberId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResult<>("The member doesn't exist"));
        }
        if (memberService.checkExists(memberRequest.getUsername(), memberId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The username is already exists"));
        }
        memberService.update(memberId, memberRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                memberService.getById(memberId)));
    }

    @Operation(summary = "刪除會員", description = "刪除一名指定會員的資訊，相當於停用該會員的帳號。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功"),
    })
    @DeleteMapping(value = "/members/{memberId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<Object>> delete(
            @PathVariable Integer memberId) {
        memberService.delete(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                null
        ));
    }
}
