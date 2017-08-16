package com.itingxi.model;

/**
 * Created by quanhai on 2016/12/17.
 */

public class Channel {

    private String classId;
    private String className;
    private String sonClass;
    private String feartherClass;
    private String isLast;

    @Override
    public String toString() {
        return "Channel{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", sonClass='" + sonClass + '\'' +
                ", feartherClass='" + feartherClass + '\'' +
                ", isLast=" + isLast +
                '}';
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSonClass() {
        return sonClass;
    }

    public void setSonClass(String sonClass) {
        this.sonClass = sonClass;
    }

    public String getFeartherClass() {
        return feartherClass;
    }

    public void setFeartherClass(String feartherClass) {
        this.feartherClass = feartherClass;
    }

    public String getIsLast() {
        return isLast;
    }

    public void setIsLast(String isLast) {
        this.isLast = isLast;
    }

    public Channel(String isLast, String classId, String className, String sonClass, String feartherClass) {
        this.isLast = isLast;
        this.classId = classId;
        this.className = className;
        this.sonClass = sonClass;
        this.feartherClass = feartherClass;
    }
}
