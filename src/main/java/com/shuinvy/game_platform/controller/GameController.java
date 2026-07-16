package com.shuinvy.game_platform.controller;

import com.shuinvy.game_platform.aspect.Logging;
import com.shuinvy.game_platform.constant.Common;
import com.shuinvy.game_platform.constant.NotifyType;
import com.shuinvy.game_platform.dto.ApiResult;
import com.shuinvy.game_platform.dto.GameRequest;
import com.shuinvy.game_platform.dto.GameResponse;
import com.shuinvy.game_platform.model.Picture;
import com.shuinvy.game_platform.model.TypeMapping;
import com.shuinvy.game_platform.service.*;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "Game Controller", description = "平台遊戲設定相關的 API")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private TypeMappingService typeMappingService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private FileService fileService;

    @Autowired
    private NotificationService notificationService;

    @Operation(summary = "取得遊戲資訊", description = "取得指定遊戲的設定資訊。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳該遊戲資訊"),
            @ApiResponse(
                    responseCode = "404",
                    description = "找不到該遊戲資訊",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    ))
    })
    @GetMapping(value = "/games/{gameId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<GameResponse>> getById(
            @PathVariable Integer gameId) {
        if (gameService.checkExists(gameId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResult<>("The game does not exist"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                gameService.getById(gameId)));
    }

    @Operation(summary = "取得遊戲列表", description = "取得所有遊戲的相關設定的列表。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳遊戲列表"),
    })
    @GetMapping(value = "/games",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<List<GameResponse>>> getList() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(Common.SUCCESS,
                Common.SuccessMessage,
                gameService.getList()));
    }

    @Operation(summary = "新增遊戲", description = "新增一筆遊戲的設定資訊，若會員有新遊戲通知設定，則會發送Email給該會員。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳新增的遊戲資訊"),
            @ApiResponse(
                    responseCode = "400",
                    description = "參數異常",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )),
    })
    @PostMapping(value = "/games",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<GameResponse>> create(
            @Validated(GameRequest.OnCreate.class) @RequestBody GameRequest gameRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>(errorMsg));
        }
        if (gameRequest.getPrice().compareTo(new BigDecimal(0)) <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The price should be greater or equal to 0"));
        }
        if (!gameService.checkUnique(gameRequest.getName(), 0)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The name is already exists"));
        }
        Integer newId = gameService.create(gameRequest);
        if (newId == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("Cannot create game"));
        }
        if (!gameRequest.getGameTypes().isEmpty()) {
            typeMappingService.update(newId, gameRequest.getGameTypes());
        }
        if (!gameRequest.getPictures().isEmpty()) {
            pictureService.updateIdsByGameId(gameRequest.getPictures(), newId);
        }
        // Send email if member need the notification
        notificationService.sendNotificationByType(
                NotifyType.New_Game,
                newId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                gameService.getById(newId)));
    }

    @Operation(summary = "修改遊戲", description = "修改指定遊戲的設定資訊。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳修改後的遊戲資訊"),
            @ApiResponse(
                    responseCode = "400",
                    description = "參數異常",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "找不到該遊戲資訊",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    ))
    })
    @PutMapping(value = "/games/{gameId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<GameResponse>> update(
            @PathVariable Integer gameId,
            @Validated(GameRequest.OnUpdate.class) @RequestBody GameRequest gameRequest,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>(errorMsg));
        }
        if (gameService.checkExists(gameId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResult<>("The game doesn't exist"));
        }
        if (!gameService.checkUnique(gameRequest.getName(), gameId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("The name is already exists"));
        }
        gameService.update(gameId, gameRequest);
        if (gameRequest.getGameTypes() != null
                && !gameRequest.getGameTypes().isEmpty()) {
            typeMappingService.update(gameId, gameRequest.getGameTypes());
        }
        if (gameRequest.getPictures() != null
                && !gameRequest.getPictures().isEmpty()) {
            pictureService.updateIdsByGameId(gameRequest.getPictures(), gameId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                gameService.getById(gameId)));
    }

    @Operation(summary = "刪除遊戲", description = "刪除指定的遊戲設定資訊，連同該遊戲原本使用的圖片資源也會刪除。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功"),
    })
    @DeleteMapping(value = "/games/{gameId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<ApiResult<Object>> delete(
            @PathVariable Integer gameId) throws IOException {
        List<Integer> deleteTypeMappings = new ArrayList<>();
        List<TypeMapping> typeMappings = typeMappingService.getByGameId(gameId);
        for (TypeMapping typeMapping : typeMappings) {
            deleteTypeMappings.add(typeMapping.getId());
        }
        if (!deleteTypeMappings.isEmpty()) {
            typeMappingService.deleteByIds(deleteTypeMappings);
        }
        List<Integer> deletePictures = new ArrayList<>();
        GameResponse gameResponse = gameService.getById(gameId);
        if (gameResponse != null && !gameResponse.getPictures().isEmpty()) {
            for (Picture picture : gameResponse.getPictures()) {
                deletePictures.add(picture.getId());
                fileService.delete(picture.getPath());
            }
        }
        if  (!deletePictures.isEmpty()) {
            pictureService.deleteByIds(deletePictures);
        }
        gameService.delete(gameId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(
                Common.SUCCESS,
                Common.SuccessMessage,
                null
        ));
    }
}
