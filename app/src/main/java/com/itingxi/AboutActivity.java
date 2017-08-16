package com.itingxi;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.itingxi.update.http.AppUpdateManager;
import com.umeng.analytics.MobclickAgent;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        String versionName = null;

        try {
            versionName = this.getPackageManager().getPackageInfo(this.getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }
        TextView textView1 = (TextView) findViewById(R.id.about_versionCode);
        TextView textView = (TextView) findViewById(R.id.about_textView);

        StringBuilder text = new StringBuilder();
        textView1.setText("版本号:"+versionName+".");
        text.append("更新日期:2017-08-11\n");
        text.append("官网:www.itingxi.com\n");
        text.append("开发者:小郭\n");
        text.append("小郭微信:xiaoguo2050\n");
        text.append("微信公众号:爱听戏\n");
        textView.setText(text.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppUpdateManager.getInstance(this).appForeground();
        MobclickAgent.onPageStart("about");
        MobclickAgent.onResume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        AppUpdateManager.getInstance(this).appBackground();
        MobclickAgent.onPageEnd("about");
        MobclickAgent.onPause(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}
