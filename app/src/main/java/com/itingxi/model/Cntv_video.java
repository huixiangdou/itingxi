package com.itingxi.model;

/**
 * Created by quanhai on 2017/8/11.
 */

public class Cntv_video {
    public Cntv_video(String cntv_m3u8_url) {
        this.cntv_m3u8_url = cntv_m3u8_url;
    }

    public String getCntv_m3u8_url() {
        return cntv_m3u8_url;
    }

    public void setCntv_m3u8_url(String cntv_m3u8_url) {
        this.cntv_m3u8_url = cntv_m3u8_url;
    }

    @Override
    public String toString() {
        return "Cntv_video{" +
                "cntv_m3u8_url='" + cntv_m3u8_url + '\'' +
                '}';
    }

    private String cntv_m3u8_url;
}
