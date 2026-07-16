package com.shuinvy.game_platform.controller;

import com.shuinvy.game_platform.aspect.Logging;
import com.shuinvy.game_platform.constant.Common;
import com.shuinvy.game_platform.dto.ApiResult;
import com.shuinvy.game_platform.dto.PaymentLogRequest;
import com.shuinvy.game_platform.model.PaymentLog;
import com.shuinvy.game_platform.service.ExcelService;
import com.shuinvy.game_platform.service.GameService;
import com.shuinvy.game_platform.service.MemberService;
import com.shuinvy.game_platform.service.PaymentLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Tag(name = "Payment Log Controller", description = "消費紀錄相關的 API")
public class PaymentLogController {

    private final String DatePattern = "yyyy-MM-dd HH:mm:ss";
    private final String FilePrefixPattern = "yyyy-MM-dd";

    @Autowired
    private PaymentLogService paymentLogService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private GameService gameService;

    @Autowired
    private ExcelService excelService;

    @Operation(summary = "取得消費紀錄列表", description = "取得會員的消費紀錄列表，並以陣列回傳。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳所有會員的消費紀錄列表"),
    })
    @GetMapping(value = "/payment_logs",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<List<PaymentLog>>> getList() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(Common.SUCCESS,
                Common.SuccessMessage,
                paymentLogService.getList()));
    }

    @Operation(summary = "新增消費紀錄", description = "新增一筆會員消費紀錄，此為後台人員操作測試使用。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳新增的消費紀錄資訊"),
            @ApiResponse(
                    responseCode = "400",
                    description = "參數異常",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )),
    })
    @PostMapping(value = "/payment_logs",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<PaymentLog>> create(
            @RequestBody PaymentLogRequest request) {
        if (request.getMemberId() == null
                || request.getGameId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The required parameters cannot be empty"));
        }
        if (memberService.getById(request.getMemberId()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The member does not exist"));
        }
        if (gameService.checkExists(request.getGameId()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The game does not exist"));
        }
        if (paymentLogService.checkExist(request.getMemberId(), request.getGameId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The member already bought the game"));
        }
        Integer newId = paymentLogService.create(request);
        if (newId == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("Cannot create payment log"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                paymentLogService.getById(newId)));
    }

    @Operation(summary = "匯出消費紀錄", description = "取得所有會員的消費紀錄，並匯出成Excel檔案提供下載。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則跳出Excel檔案下載要求"),
    })
    @GetMapping("/payment_logs/export")
    public void export(HttpServletResponse response)
            throws SQLException, IOException {
        List<String> labels = new ArrayList<>(
                List.of("Member Id", "Member Name",
                        "Game Id", "Game Name",
                        "Price", "Purchase Date"));
        List<PaymentLog> list = paymentLogService.getList();
        List<List<String>> data  = new ArrayList<>();
        List<String> row;
        DateFormat df = new SimpleDateFormat(DatePattern);
        for (PaymentLog paymentLog : list) {
            row = new ArrayList<>();
            row.add(String.valueOf(paymentLog.getMemberId()));
            row.add(paymentLog.getMemberName());
            row.add(String.valueOf(paymentLog.getGameId()));
            row.add(paymentLog.getGameName());
            row.add(paymentLog.getPoint().toString());
            row.add(df.format(paymentLog.getCreated()));
            data.add(row);
        }
        Workbook workbook = excelService.export(labels, data);

        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        DateFormat prefixDF = new SimpleDateFormat(FilePrefixPattern);
        String datePrefix = prefixDF.format(new Date());
        String fileName = "";
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=%s_Payment_Log.xlsx".formatted(datePrefix));

        ServletOutputStream os = response.getOutputStream();

        workbook.write(os);

        os.flush();

        workbook.close();
    }
}
