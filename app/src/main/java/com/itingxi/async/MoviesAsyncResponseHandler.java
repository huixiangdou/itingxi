package com.itingxi.async;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.itingxi.Application;
import com.itingxi.interFace.AsyncClientInterface;
import com.itingxi.model.Movies;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by quanhai on 19/10/2016.
 * 获取主页数据
 */

public class MoviesAsyncResponseHandler extends JsonHttpResponseHandler {

    private AsyncClientInterface asyncClientInterface;
    private ArrayList<Movies> movieArrayList;
    private Context context;
    private ProgressDialog progressDialog;
    private String name;

    /**
     * 初始类
     * @param context
     * @param asyncClientInterface
     */

    public MoviesAsyncResponseHandler(Context context, AsyncClientInterface asyncClientInterface,String name){
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

        if (statusCode == 200){
            Application.statusCode = true;
        }

        Log.d("statusCode", String.valueOf(statusCode));

        try {
            if (response.opt(name).equals(null)) {
                Toast.makeText(context,"搜索结果为空,请缩短关键词再试！",Toast.LENGTH_SHORT).show();
                return;
            }else {
                JSONArray jsonArray = response.getJSONArray(name);

                movieArrayList = new ArrayList<Movies>();

                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                    String title = jsonObject.optString("title");
                    String id = jsonObject.optString("id");
                    String diggtop = jsonObject.optString("diggtop");
                    String onclick = jsonObject.optString("onclick");
                    String titlePic = jsonObject.optString("titlepic");
                    String playid = jsonObject.optString("playid");
                    String jumu = jsonObject.optString("jumu");
                    String player = jsonObject.optString("player");
                    String onlinepath = jsonObject.optString("onlinepath");
                    String length = jsonObject.optString("length");
                    String tags = jsonObject.optString("tags");

                    movieArrayList.add(new Movies(id, title, jumu, player, onclick, playid,
                            onlinepath, diggtop, tags, titlePic, length));
                    Log.d("title",jsonObject.optString("title"));
                }

                asyncClientInterface.onSuccess(statusCode, movieArrayList);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        asyncClientInterface.onFailure(statusCode);
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
