package com.itingxi.basic;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.itingxi.interFace.AsyncClientInterface;
import com.itingxi.model.Cntv_video;
import com.itingxi.model.Youku_video;

import java.util.ArrayList;

/**
 * Created by quanhai on 19/10/2016.
 */

public class BasicActivity extends AppCompatActivity implements AsyncClientInterface,NavigationView.OnNavigationItemSelectedListener {
    @Override
    public void onSuccess(int statusCode,ArrayList arrayList) {

    }

    @Override
    public void onSuccess(ArrayList arrayList) {

    }

    public void onSuccess(String string) {

    }

    @Override
    public void onSuccess(Youku_video youku_video) {

    }
    @Override
    public void onSuccess(Cntv_video cntv_video) {

    }

    @Override
    public void onFailure(int statusCode) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
