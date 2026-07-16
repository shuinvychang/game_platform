package com.shuinvy.game_platform.controller;

import com.shuinvy.game_platform.constant.Common;
import com.shuinvy.game_platform.dto.ApiResult;
import com.shuinvy.game_platform.model.OperationLog;
import com.shuinvy.game_platform.service.OperationLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Operation Log Controller", description = "後臺操作紀錄相關的 API")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    @Operation(summary = "取得操作紀錄", description = "取得所有後台人員操作紀錄的列表。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳操作紀錄列表"),
    })
    @GetMapping(value = "/operation_logs",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<List<OperationLog>>> getList() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(Common.SUCCESS,
                        Common.SuccessMessage,
                        operationLogService.getList()));
    }
}
