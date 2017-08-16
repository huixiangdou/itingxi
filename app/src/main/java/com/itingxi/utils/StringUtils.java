package com.itingxi.utils;

/**
 * Created by quanhai on 16/8/28.
 * String 工具类用于重新获取string
 */
public class StringUtils {
    /*
    传入数据库土豆地址,获取一段土豆地址
     */
    public static String getTvode(String tvode){
        int i = tvode.indexOf("::::::0::");
        tvode = tvode.substring(7, i);
        return tvode;
    }
}
