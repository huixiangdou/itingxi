package com.itingxi.model;

/**
 * Created by quanhai on 16/7/10.
 */
public class MoviesComment {
    public MoviesComment(String userName, String sayText) {
        this.userName = userName;
        this.sayText = sayText;
    }

    public String getSayText() {
        return sayText;
    }

    public void setSayText(String sayText) {
        this.sayText = sayText;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;
    private String sayText;
}
