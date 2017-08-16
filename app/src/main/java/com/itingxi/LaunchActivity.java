package com.itingxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

public class LaunchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Toast.makeText(getApplicationContext(),"建议WIFI环境下使用本应用",Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LaunchActivity.this,MainActivity.class));
                LaunchActivity.this.finish();
            }
        },2000);

    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("launch");
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("launch");
        MobclickAgent.onPause(this);
    }
}
