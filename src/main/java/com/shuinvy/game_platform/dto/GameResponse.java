package com.shuinvy.game_platform.dto;

import com.shuinvy.game_platform.model.Game;
import com.shuinvy.game_platform.model.Picture;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class GameResponse {

    @Schema(description = "遊戲Id", example = "1")
    private Integer id;

    @Schema(description = "遊戲名稱", example = "Game")
    private String name;

    @Schema(description = "遊戲簡介", example = "Information")
    private String info;

    @Schema(description = "遊戲詳細資訊，支援HTML語法", example = "<div>Description</div>")
    private String description;

    @Schema(description = "遊戲販售價格", example = "10.5")
    private BigDecimal price;

    @Schema(description = "是否發佈遊戲，1: 是; 0: 否", example = "1")
    private Integer isPublished;

    @Schema(description = "遊戲發佈日期，時區: UTC+8", example = "2026-07-15 00:00:00")
    private Date published;

    @Schema(description = "狀態，1: 正常; 0: 刪除", example = "1")
    private Integer status;

    @Schema(description = "新增日期，時區: UTC+8", example = "2026-07-15 00:00:00")
    private Date created;

    @Schema(description = "修改日期，時區: UTC+8", example = "2026-07-15 00:00:00")
    private Date modified;

    @Schema(description = "遊戲類型陣列，一款遊戲可擁有多種類型")
    private List<GameTypeResponse> gameTypes;

    @Schema(description = "圖片資訊陣列，一款遊戲最多5張截圖")
    private List<Picture> pictures;

    public GameResponse() {}

    public GameResponse(
            Game game,
            List<GameTypeResponse> gameTypes
    ) {
        this.id = game.getId();
        this.name = game.getName();
        this.info = game.getInfo();
        this.description = game.getDescription();
        this.price = game.getPrice();
        this.isPublished = game.getIsPublished();
        this.published = game.getPublished();
        this.status = game.getStatus();
        this.created = game.getCreated();
        this.modified = game.getModified();
        this.gameTypes = gameTypes;
    }

    public GameResponse(
            Game game,
            List<GameTypeResponse> gameTypes,
            List<Picture> pictures) {
        this.id = game.getId();
        this.name = game.getName();
        this.info = game.getInfo();
        this.description = game.getDescription();
        this.price = game.getPrice();
        this.isPublished = game.getIsPublished();
        this.published = game.getPublished();
        this.status = game.getStatus();
        this.created = game.getCreated();
        this.modified = game.getModified();
        this.gameTypes = gameTypes;
        this.pictures = pictures;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public List<GameTypeResponse> getGameTypes() {
        return gameTypes;
    }

    public void setGameTypes(List<GameTypeResponse> gameTypes) {
        this.gameTypes = gameTypes;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }
}
