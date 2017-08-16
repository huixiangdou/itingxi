package com.itingxi.async;

import android.content.Context;
import android.util.Log;

import com.itingxi.interFace.AsyncClientInterface;
import com.itingxi.model.Youku_video;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by quanhai on 2017/7/20.
 * 获取优酷视频地址
 */

public class YoukuAsyncResponseHandler extends JsonHttpResponseHandler {

    private AsyncClientInterface asyncClientface;
    private Context context;
    private String youku_mp4_url;
    private String youku_m3u8_url;
    private String name;
    private Youku_video youku_video;


    /**
     * 初始化类
     */
    public YoukuAsyncResponseHandler(Context context, AsyncClientInterface asyncClientInterface, String name) {
        this.context = context;
        this.asyncClientface = asyncClientInterface;
        this.name = name;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        Log.d("youkuasyncResponseHandler",response.toString());
        youku_mp4_url = new String();
        youku_m3u8_url = new String();

        try {
            JSONArray jsonArray = response.getJSONArray("youku_video");
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            youku_mp4_url = jsonObject.optString("youku_mp4_url");
            youku_m3u8_url = jsonObject.optString("youku_m3u8_url");
            youku_video = new Youku_video(youku_m3u8_url,youku_mp4_url);
            Log.d("youkuAsync_url",youku_video.getYouku_mp4_url());
            asyncClientface.onSuccess(youku_video);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }
}
