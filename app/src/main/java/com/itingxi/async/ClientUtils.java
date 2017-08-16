package com.itingxi.async;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

/**
 * Created by quanhai on 14/10/2016.
 *
 * 链接数据库的工具类
 */

public class ClientUtils {

    private AsyncHttpClient asyncHttpClient;

    private static ClientUtils clientUtils;

    private ClientUtils(){
        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(30000);
    }

    /**
     * 创建一个实体类
     *
     */
    public static ClientUtils getClientUtils(){
        if (clientUtils == null){
            synchronized (ClientUtils.class){
                clientUtils = new ClientUtils();
            }
        }

        return clientUtils;
    }

    /**
     * 获取网络数据
     *
     * @param url
     * @param handler
     */
    public RequestHandle getNetDate(String url, AsyncHttpResponseHandler handler){
        return asyncHttpClient.get(url,handler);
    }
}
