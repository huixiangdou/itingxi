//package com.itingxi.utils;
//
//import android.content.Context;
//import android.util.Log;
//import android.widget.RelativeLayout;
//
//import com.baidu.mobads.AdSettings;
//import com.baidu.mobads.AdView;
//import com.baidu.mobads.AdViewListener;
//
//import org.json.JSONObject;
//
///**
// * Created by quanhai on 9/17/16.
// */
//public class BaiduAD {
//    /*
//    banner广告
//    参数1:广告id
//        2:layout
//        3: context
//     */
//    public static void adBanner(Context context, String id, RelativeLayout layout){
//        AdView adView = new AdView(context,id);
//        AdSettings.setKey(new String[]{"baidu","中国"});
//        adView.setListener(new AdViewListener() {
//            public void onAdSwitch() {
//                Log.w("", "onAdSwitch");
//            }
//
//            public void onAdShow(JSONObject info) {
//                // 广告已经渲染出来
//                Log.w("", "onAdShow " + info.toString());
//            }
//
//            public void onAdReady(AdView adView) {
//                // 资源已经缓存完毕，还没有渲染出来
//                Log.w("", "onAdReady " + adView);
//            }
//
//            public void onAdFailed(String reason) {
//                Log.w("", "onAdFailed " + reason);
//            }
//
//            public void onAdClick(JSONObject info) {
//                // Log.w("", "onAdClick " + info.toString());
//
//            }
//
//            @Override
//            public void onAdClose(JSONObject arg0) {
//                Log.w("", "onAdClose");
//            }
//        });
//        // 将adView添加到父控件中(注：该父控件不一定为您的根控件，只要该控件能通过addView能添加广告视图即可)
//        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT);
//        rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        layout.addView(adView,rllp);
//    }
//}
