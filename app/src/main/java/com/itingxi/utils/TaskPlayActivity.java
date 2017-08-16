package com.itingxi.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.itingxi.R;

public class TaskPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_play);
        Intent intent = getIntent();
        String movie_play_url = intent.getStringExtra("PLAY_URL");
    }

}
