package com.shuinvy.game_platform.dto;

import com.shuinvy.game_platform.constant.Common;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApiResult<T> {

    @Schema(description = "狀態碼，1: 成功; 其他: 異常", example = "1")
    private final Integer code;

    @Schema(description = "錯誤訊息", example = "success")
    private final String message;

    @Schema(description = "回傳資訊", example = "[]")
    private final T data;

    public ApiResult(String errorMessage) {
        this.code = Common.FAILURE;
        this.message = errorMessage;
        this.data = null;
    }

    public ApiResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}
