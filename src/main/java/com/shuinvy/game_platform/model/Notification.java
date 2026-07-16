package com.shuinvy.game_platform.model;

import java.util.Date;

public class Notification {

    private Integer id;
    private Integer memberId;
    private Integer isNewGame;
    private Date created;
    private Date modified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getIsNewGame() {
        return isNewGame;
    }

    public void setIsNewGame(Integer isNewGame) {
        this.isNewGame = isNewGame;
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
}
