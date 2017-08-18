package com.itingxi.update.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.itingxi.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by quanhai on 16/7/12.
 */
public class CheckUpdateAsynResponseHandler extends JsonHttpResponseHandler {

    private Context mContext;
    private ProgressDialog progressDialog;

    private OnCheckUpdateListener listener;
    public CheckUpdateAsynResponseHandler(Context context, OnCheckUpdateListener listener) {
        this.mContext  = context;
        this.listener = listener;

        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(context.getString(R.string.tip_requesting));
        progressDialog.setCancelable(true);
    }



    public interface OnCheckUpdateListener {
        void onSuccess(UpdateInfo updateInfo);
        void onFailure();
    }

    /**
     * 请求开始时回调
     */
    @Override
    public void onStart() {
        super.onStart();
        progressDialog.show();
    }

    /**
     * 响应成功时回调
     * @param statusCode
     * @param headers
     * @param response
     */
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);

        if (response != null) {
            Log.d("info", response.toString());


            UpdateInfo updateInfo = new UpdateInfo();
            try {
                // 对数据进行解析
                JSONObject ireaderObj = response.optJSONObject("android_ireader");
                if (ireaderObj != null) {
                    updateInfo.setName(ireaderObj.optString("name"));
                    updateInfo.setVersionName(ireaderObj.optString("app_version"));
//                    updateInfo.setVersionCode(ireaderObj.optInt("version_code"));
                    updateInfo.setVersionCode(2081702);
                    updateInfo.setFeatures(ireaderObj.optString("features"));
//                    updateInfo.setUpdateUrl(ireaderObj.optString("update_url"));
                    updateInfo.setUpdateUrl("http://itingxi-10012740.cossh.myqcloud.com/apk/2017/0219/itingxi.apk");
                    updateInfo.setSdkVersion(ireaderObj.optString("sdk_version"));
                    updateInfo.setOsVersion(ireaderObj.optString("os_version"));
                    Log.d("update_url",ireaderObj.optString("update_url"));

                    // 解析json数据
                    listener.onSuccess(updateInfo);
                    updateInfo.setUpdateUrl(ireaderObj.optString("update_url"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }




    }


    /**
     * 请求结束时回调
     */
    @Override
    public void onFinish() {
        super.onFinish();
        progressDialog.dismiss();
    }

    /**
     * 响应失败时回调
     * @param statusCode
     * @param headers
     * @param responseString
     * @param throwable
     */
    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);

        listener.onFailure();
    }


    @Override
    public void onCancel() {
        super.onCancel();
        progressDialog.cancel();
    }
}
