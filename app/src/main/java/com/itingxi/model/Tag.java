package com.itingxi.model;

/**
 * Created by quanhai on 16/7/12.
 */
public class Tag {
    public Tag(String tagName) {
        this.tagName = tagName;
    }
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    private String tagName;
}
