package com.itingxi.async;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.itingxi.interFace.AsyncClientInterface;
import com.itingxi.model.Channel;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by quanhai on 19/10/2016.
 * 获取栏目数据
 */

public class ChannelAsyncResponseHandler extends JsonHttpResponseHandler {

    private AsyncClientInterface asyncClientInterface;
    private ArrayList<Channel> chnanelArrayList;
    private Context context;
    private ProgressDialog progressDialog;
    private String name;

    /**
     * 初始类
     * @param context
     * @param asyncClientInterface
     */

    public ChannelAsyncResponseHandler(Context context, AsyncClientInterface asyncClientInterface, String name){
        this.context = context;
        this.asyncClientInterface = asyncClientInterface;
        this.name = name;

        //定义diglog
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("努力加载中......");
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);

        try {
            if (response.opt(name).equals(null)) {
                Toast.makeText(context,"搜索结果为空,请缩短关键词再试！",Toast.LENGTH_SHORT).show();
                return;
            }else {
                JSONArray jsonArray = response.getJSONArray(name);

                chnanelArrayList = new ArrayList<Channel>();

                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                    String isLast = jsonObject.optString("islast");
                    String classid = jsonObject.optString("classid");
                    String classname = jsonObject.optString("classname");
                    String sonclass = jsonObject.optString("sonclass");
                    String feartherclass = jsonObject.optString("feartherclass");

                    chnanelArrayList.add(new Channel(isLast, classid, classname, sonclass, feartherclass));
                    Log.d("title",jsonObject.optString("title"));
                }

                asyncClientInterface.onSuccess(statusCode, chnanelArrayList);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }


    @Override
    public void onStart() {
        super.onStart();
        progressDialog.show();
    }

    @Override
    public void onFinish() {
        super.onFinish();
        progressDialog.dismiss();
    }


}
