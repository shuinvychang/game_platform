package com.shuinvy.game_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class GameRequest extends BaseRequest {

    public interface OnCreate {}
    public interface OnUpdate {}

    @Schema(description = "遊戲名稱", example = "Game")
    @NotBlank(message = "The name field cannot be empty", groups = OnCreate.class)
    @Size(max = 13, message = "The name field should be less than 13 characters", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Schema(description = "遊戲簡介", example = "Information")
    @Size(max = 30, message = "The info field should be less than 30 characters", groups = {OnCreate.class, OnUpdate.class})
    private String info;

    @Schema(description = "遊戲詳細說明，支援html語法", example = "<div>Description</div>")
    private String description;

    @Schema(description = "遊戲販售價格", example = "10.5")
    private BigDecimal price;

    @Schema(description = "是否發佈遊戲，1: 是; 0: 否", example = "1")
    private Integer isPublished;

    @Schema(description = "遊戲發佈日期，格式: yyyy-mm-dd H:i:s，UTC+8", example = "2026-07-15 00:00:00")
    private Date published;

    @Schema(description = "狀態，1: 正常; 0: 刪除", example = "1")
    private Integer status;

    @Schema(description = "遊戲類型陣列，一款遊戲可以包含多種類型", example = "[1, 2, 3]")
    private List<Integer> gameTypes;

    @Schema(description = "圖片Id陣列，一款遊戲最多5張截圖", example = "[1, 2, 3, 4, 5]")
    private List<Integer> pictures;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Integer isPublished) {
        this.isPublished = isPublished;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Integer> getGameTypes() {
        return gameTypes;
    }

    public void setGameTypes(List<Integer> gameTypes) {
        this.gameTypes = gameTypes;
    }

    public List<Integer> getPictures() {
        return pictures;
    }

    public void setPictures(List<Integer> pictures) {
        this.pictures = pictures;
    }
}
