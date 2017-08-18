package com.itingxi;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.itingxi.update.http.AppUpdateManager;
import com.umeng.analytics.MobclickAgent;

public class AboutActivity extends AppCompatActivity {
    private TextView txtLog;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        txtLog = (TextView) findViewById(R.id.txt_log);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        Button button = (Button) findViewById(R.id.check_update);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                txtLog.setText("检查更新中...");
                dialog.show();
                Log.d("click","click");
                BDAutoUpdateSDK.uiUpdateAction(AboutActivity.this, new MyUICheckUpdateCallback());
            }
        });

        String versionName = null;

        try {
            versionName = this.getPackageManager().getPackageInfo(this.getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }
        TextView textView1 = (TextView) findViewById(R.id.about_versionCode);
        TextView textView = (TextView) findViewById(R.id.about_textView);

        StringBuilder text = new StringBuilder();
        textView1.setText("版本号:"+versionName+".");
        text.append("更新日期:2017-08-17\n");
        text.append("官网:www.itingxi.com\n");
        text.append("开发者:小郭\n");
        text.append("小郭微信:xiaoguo2050\n");
        text.append("微信公众号:爱听戏\n");
        textView.setText(text.toString());
    }

    //默认UI更新
    public class MyUICheckUpdateCallback implements UICheckUpdateCallback {

        @Override
        public void onNoUpdateFound() {

        }

        @Override
        public void onCheckComplete() {
            Toast.makeText(AboutActivity.this,"您的版本为最新哦",Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }

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
    protected void onDestroy() {
        dialog.dismiss();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
