package com.itingxi.update.http;

import android.content.Context;

import com.itingxi.update.utils.LogUtils;
import com.itingxi.update.utils.NetworkUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

/**
 * Created by quanhai on 16/7/12.
 */
public class HttpRequestHelper {
    private static AsyncHttpClient client;

    private static HttpRequestHelper mInstance;

    private HttpRequestHelper() {
        if (client == null) {
            client = new AsyncHttpClient();
            client.setUserAgent("android ireader");
            client.setTimeout(15000);
        }
    }

    public static HttpRequestHelper getInstance() {
        if (mInstance == null) {
            synchronized (HttpRequestHelper.class) {
                if (mInstance == null) {
                    mInstance = new HttpRequestHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * GET请求
     *
     * @param mRequestURL
     *            请求URL
     * @param mHandler
     *            数据处理句柄
     */
    public void getHttpRequestAsync(String mRequestURL,
                                    AsyncHttpResponseHandler mHandler) {
        client.get(mRequestURL, mHandler);
    }

    /**
     * POST请求
     *
     * @param mRequestURL
     *            请求
     * @param params
     *            请求参数
     * @param mHandler
     *            数据处理句柄
     */
    public void postHttpRequestAsync(String mRequestURL, RequestParams params,
                                     AsyncHttpResponseHandler mHandler) {
        client.post(mRequestURL, params, mHandler);
    }

    /**
     * GET请求
     *
     * @param mContext
     *            上下文菜单
     * @param url
     *            请求URL
     * @param params
     *            请求参数
     * @param mHandler
     *            处理句柄
     */
    public void getHttpRequestAsync(Context mContext, String url,
                                    RequestParams params, AsyncHttpResponseHandler mHandler) {
        if (NetworkUtils.isNetworkAvailable(mContext)) {
            client.get(url, params, mHandler);
        }
    }

    /**
     * HEAD请求
     *
     * @param mContext
     * @param url
     * @param mHandler
     */
    public void headHttpRequestAsyns(Context mContext, String url,
                                     AsyncHttpResponseHandler mHandler) {
        client.head(mContext, url, mHandler);
    }

    /**
     * 检查更新
     *
     * @param params
     * @param responseHandler
     */
    public RequestHandle checkUpdate(RequestParams params,
                                     AsyncHttpResponseHandler responseHandler) {
        LogUtils.e("checkUpdate", URLHelper.CHECK_UPDATE + "?" + params);
        return client.get(URLHelper.CHECK_UPDATE, params, responseHandler);
    }



}
