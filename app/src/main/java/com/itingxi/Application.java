package com.itingxi;

import android.content.Context;

import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadLog;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Jacksgong on 12/17/15.
 * 定义全局变量和方法
 */
public class Application extends android.app.Application {
    public final static String URL_MESSAGE = "com.itingxi.URL";
    public final static String TEXT_MESSAGE = "com.itingxi.TEXT_MESSAGE";
    public final static String ITINGXI_URL = "http://www.itingxi.com/e/extend";
    public final static String APP_URL = "/app220";
    public final static String getMovies = ITINGXI_URL + APP_URL + "/getMoviesJson.php";//负责主页
    public final static String getTagsJson = ITINGXI_URL + APP_URL + "/getTagsJson.php?tag=";//负责标签
    public final static String getKeywordJson = ITINGXI_URL + APP_URL + "/getKeywordJson.php?keyword=";//负责搜索
    public final static String getChannelMovies = ITINGXI_URL + APP_URL + "/getChannelMovies.php?classId=";//负责栏目数据
    public final static String YOUKU_ANDROID = ITINGXI_URL + "/youku/20170808/youku_android.php?url=";//获取优酷视频信息
    public final static String CNTV_ANDROID = ITINGXI_URL + "/youku/20170808/cntv_android.php?url=";//获取优酷视频信息
    public static boolean statusCode = false;
    public static final String DATABASE_NAME = "itingxi.db";//数据库名称
    public static final int DATABASE_VERSION = 5;//数据库版本号

    public static Context CONTEXT;
    @Override
    public void onCreate() {
        super.onCreate();
        // for demo.
        CONTEXT = this;

        // just for open the log in this demo project.
        FileDownloadLog.NEED_LOG = false;

        /**
         * just for cache Application's Context, and ':filedownloader' progress will NOT be launched
         * by below code, so please do not worry about performance.
         * @see FileDownloader#init(Context)
         */
        FileDownloader.init(getApplicationContext(),
                new FileDownloadHelper.OkHttpClientCustomMaker() { // is not has to provide.
                    @Override
                    public OkHttpClient customMake() {
                        // just for OkHttpClient customize.
                        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
                        // you can set the connection timeout.
                        builder.connectTimeout(15_000, TimeUnit.MILLISECONDS);
                        // you can set the HTTP proxy.
                        builder.proxy(Proxy.NO_PROXY);
                        // etc.
                        return builder.build();
                    }
                });
    }
}
