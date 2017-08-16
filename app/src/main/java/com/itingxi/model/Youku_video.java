package com.itingxi.model;

/**
 * Created by quanhai on 2017/8/8.
 */

public class Youku_video {
    private String youku_m3u8_url;
    private String youku_mp4_url;

    @Override
    public String toString() {
        return "Youku_video{" +
                "youku_m3u8_url='" + youku_m3u8_url + '\'' +
                ", youku_mp4_url='" + youku_mp4_url + '\'' +
                '}';
    }

    public String getYouku_m3u8_url() {
        return youku_m3u8_url;
    }

    public void setYouku_m3u8_url(String youku_m3u8_url) {
        this.youku_m3u8_url = youku_m3u8_url;
    }

    public String getYouku_mp4_url() {
        return youku_mp4_url;
    }

    public void setYouku_mp4_url(String youku_mp4_url) {
        this.youku_mp4_url = youku_mp4_url;
    }

    public Youku_video(String youku_m3u8_url, String youku_mp4_url) {
        this.youku_m3u8_url = youku_m3u8_url;
        this.youku_mp4_url = youku_mp4_url;
    }


}
