package com.itingxi.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by quanhai on 16/7/7.
 */
public class HttpUtils {
//    private static final String LOG_TAG = null;
//    private static String url;
//    private static String id;

    /**
     * @param url
     * @param handler
     */
    public static void getURLjson(final String url,final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is;
                HttpURLConnection connection ;
                try {
                    connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestMethod("GET");
                    is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line = "";
                    StringBuilder result = new StringBuilder();
                    while ((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    Message message = new Message();
                    message.obj = result.toString();
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /**
     * 获取视频绝对地址
     */
    public static String getVideoUrl(final String url) {
        final String[] videoUrl = new String[1];
        new Thread(new Runnable() {
            @Override
            public void run() {

                InputStream is;
                HttpURLConnection conn;
                try {
                    conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.setRequestMethod("GET");
                    is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                    String line = "";
                    StringBuilder result = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    videoUrl[0] = result.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();

        return videoUrl[0];
    }
    /*
    获取新闻列表过时的工具类
     */
    public static void getNewsJson(final String url, final Handler handler) {
        new Thread(new Runnable() {

            @Override
            public void run() {

                InputStream is;
                HttpURLConnection conn;
                try {
                    conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.setRequestMethod("GET");
                    is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                    String line = "";
                    StringBuilder result = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    Message msg = new Message();
                    msg.obj = result.toString();
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    /*
    获取自定义tag列表
    */
    public static void getTagJson(final String url,final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is;
                HttpURLConnection conn;
                try {
                    conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.setRequestMethod("GET");
                    is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                    String line = "";
                    StringBuilder result = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    Message msg = new Message();
                    msg.obj = result.toString();
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    /*
    获取列表图片地址并赋值
     */
    public static void setPicBitmap(final ImageView ivpic, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    ivpic.setImageBitmap(bitmap);
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
    增加文章点击
     */
    public static void setOnclick(final String id) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String path = "http://www.itingxi.com/e/extend/m/setclick.php?id=" + id;
                    HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
                    conn.connect();
                    conn.getResponseMessage();
                    InputStream is = conn.getInputStream();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    /*
    添加评论
    http://www.itingxi.com/e/extend/m/setPinglun.php?id=4807&mac=123&saytext=123
    */
    public static void setPinglun(final String id, final String mac, final String saytext) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = "http://www.itingxi.com/e/extend/m/setPinglun.php?" +
                            "id=" + id + "&mac=" + "'" + mac + "'" + "&saytext=" + "'" + saytext + "'";
                    HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
                    conn.connect();
                    conn.getResponseMessage();
                    InputStream is = conn.getInputStream();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
    获取mac地址
     */
    public static String getMacAddress(Context mContext) {
        String macStr = "";
        WifiManager wifiManager = (WifiManager) mContext
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getMacAddress() != null) {
            macStr = wifiInfo.getMacAddress();// MAC地址
        } else {
            macStr = "null";
        }

        return macStr;
    }
}
