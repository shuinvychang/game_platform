package com.shuinvy.game_platform.controller;

import com.shuinvy.game_platform.constant.Common;
import com.shuinvy.game_platform.dto.ApiResult;
import com.shuinvy.game_platform.dto.PictureRequest;
import com.shuinvy.game_platform.model.Picture;
import com.shuinvy.game_platform.service.FileService;
import com.shuinvy.game_platform.service.PictureService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "Picture Controller", description = "圖片資源相關的 API")
public class PictureController {

    private final Integer MAX_UPLOAD_COUNT = 5;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private FileService fileService;

    @Operation(summary = "取得圖片或資源列表", description = "取得所有圖片或資源資訊，並以陣列回傳。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功回傳圖片或資源列表"),
    })
    @GetMapping(value = "/pictures",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<List<Picture>>> getList() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(Common.SUCCESS,
                Common.SuccessMessage,
                pictureService.getList()));
    }

    @Operation(summary = "上傳圖片", description = "將圖片上傳到伺服器儲存，支援多張圖片上傳。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功則回傳上傳的圖片列表"),
            @ApiResponse(
                    responseCode = "400",
                    description = "參數異常",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResult.class)
                    )),
    })
    @PostMapping(value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<List<Picture>>> upload(
            @RequestPart("files") MultipartFile[] files,
            @RequestParam(required = false) List<Integer> oldList) throws IOException {
        List<Picture> pictureList = new ArrayList<>();
        PictureRequest picture;
        Integer pictureId;
        if (files == null || files.length == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>("No file to be uploaded"));
        }
        if (files.length > MAX_UPLOAD_COUNT) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResult<>(
                    "Only allow %s files in maximum to be uploaded"
                    .formatted(MAX_UPLOAD_COUNT)));
        }
        if (oldList != null && !oldList.isEmpty()) {
            boolean resultOfDeleting = deleteByList(oldList);
            if (resultOfDeleting)
                pictureService.deleteByIds(oldList);
        }
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            String fileContentType = file.getContentType();
            String newFileName = fileService.save(file);
            picture = new PictureRequest(newFileName, fileContentType);
            pictureId = pictureService.create(picture);
            if (pictureId == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResult<>("Cannot create picture"));
            }
            pictureList.add(pictureService.getById(pictureId));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResult<>(Common.SUCCESS,
                Common.SuccessMessage,
                pictureList));
    }

    private boolean deleteByList(List<Integer> ids) throws IOException {
        List<Picture> pictures = pictureService.getListByIds(ids);
        boolean result = false;
        for (Picture picture : pictures) {
            String fileName = picture.getPath();
            result = fileService.delete(fileName);
        }
        return result;
    }
}
