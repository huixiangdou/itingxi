package com.itingxi.async;

import android.content.Context;
import android.util.Log;

import com.itingxi.interFace.AsyncClientInterface;
import com.itingxi.model.Cntv_video;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by quanhai on 2017/7/20.
 * 获取优酷视频地址
 */

public class CntvAsyncResponseHandler extends JsonHttpResponseHandler {

    private AsyncClientInterface asyncClientface;
    private Context context;
    private String cntv_m3u8_url;
    private String name;
    private Cntv_video cntv_video;


    /**
     * 初始化类
     */
    public CntvAsyncResponseHandler(Context context, AsyncClientInterface asyncClientInterface, String name) {
        this.context = context;
        this.asyncClientface = asyncClientInterface;
        this.name = name;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        Log.d("cntvasyncResponseHandler",response.toString());
        cntv_m3u8_url = new String();

        try {
            JSONArray jsonArray = response.getJSONArray("cntv_video");
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            cntv_m3u8_url = jsonObject.optString("cntv_m3u8_url");
            cntv_video = new Cntv_video(cntv_m3u8_url);
            Log.d("youkuAsync_url",cntv_video.getCntv_m3u8_url());
            asyncClientface.onSuccess(cntv_video);
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
