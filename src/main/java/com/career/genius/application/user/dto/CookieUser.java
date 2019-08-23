package com.career.genius.application.user.dto;

import java.io.Serializable;

/**
 * 缓存对象
 */
public class CookieUser implements Serializable {

    /**
     * 用户ID
     */
	private String userId;

    /**
     * 微信公众号appId
     */
	private String appId;

    public CookieUser(){

    }

    public CookieUser(String userId){
        this.userId = userId;
    }

    public CookieUser(String userId, String appId){
        this.userId = userId;
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}