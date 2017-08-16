package com.itingxi.async;


import android.content.Context;
import android.util.Log;

import com.itingxi.interFace.AsyncClientInterface;
import com.itingxi.model.MoviesComment;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by quanhai on 19/10/2016.
 * 同步评论数据
 */

public class CommentAsyncResponseHandler extends JsonHttpResponseHandler {

    private AsyncClientInterface asyncClientInterface;
    private Context context;
    private ArrayList<MoviesComment> moviesCommentArrayList;
    private String name;

    /**
     * 初始类
     * @param context
     * @param asyncClientInterface
     */

    public CommentAsyncResponseHandler(Context context, AsyncClientInterface asyncClientInterface,String name){
        this.context = context;
        this.asyncClientInterface = asyncClientInterface;
        this.name = name;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        Log.d("handler_pinglun",response.toString());
        try {
            JSONArray jsonArray = response.getJSONArray(name);
            moviesCommentArrayList = new ArrayList<MoviesComment>();

            for (int i = jsonArray.length() - 1; i >= 0; i--){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                String username = jsonObject.optString("username");

                if (username.trim().isEmpty()){
                    username = "匿名网友";
                }
                String saytext = jsonObject.optString("saytext");

                moviesCommentArrayList.add(new MoviesComment(username,saytext));
            }

            asyncClientInterface.onSuccess(moviesCommentArrayList);

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
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }


}
