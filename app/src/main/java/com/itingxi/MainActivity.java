package com.itingxi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.itingxi.Adapter.MoviesAdapter;
import com.itingxi.Adapter.OnRecyclerViewItemClickListener;
import com.itingxi.async.ClientUtils;
import com.itingxi.async.MoviesAsyncResponseHandler;
import com.itingxi.basic.BasicActivity;
import com.itingxi.interFace.OnLoadMoreListener;
import com.itingxi.model.Movies;
import com.itingxi.update.http.AppUpdateManager;
import com.itingxi.utils.ImageUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

public class MainActivity extends BasicActivity {

    private MoviesAdapter moviesAdapter;
    private RecyclerView recyclerView;
    private Handler handler;
    public ArrayList initArraylist;
    private TextView tvEmptyView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList moviesArraylist;
    public static MainActivity mainActivityInstace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainActivityInstace = this;

        //这是显示列表
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //检查更新
        AppUpdateManager.getInstance(MainActivity.this).checkUpdate();

        //获取数据
        getData();
    }

    private void getData(){
        //加载movies数据
        ImageUtils.initUniversalImageLoader(getApplicationContext());
        moviesArraylist = new ArrayList();
        String name = "movies";
        String url = Application.getMovies;//获取主页数据
        ClientUtils.getClientUtils().getNetDate(url,new MoviesAsyncResponseHandler(this,this,name));
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("main");
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("main");
        MobclickAgent.onPause(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }



    /**
     * 请求movies数据成功
     * @param arrayList
     */
    @Override
    public void onSuccess(int statusCode, ArrayList arrayList) {
        super.onSuccess(statusCode, arrayList);

        this.moviesArraylist = arrayList;
        Log.d("movisArraylist1", String.valueOf(moviesArraylist.size()));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_item);
        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        handler = new Handler();

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        initArraylist = new ArrayList();
        for (int i = 0; i<6;i++){
            initArraylist.add(arrayList.get(i));
        }
        moviesAdapter = new MoviesAdapter(initArraylist,recyclerView);
        recyclerView.setAdapter(moviesAdapter);

        if (initArraylist.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);

        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
        }

        moviesAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object object) {
                Intent intent = new Intent(MainActivity.this,PlayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("MOVIES", (Movies)object);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        moviesAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                initArraylist.add(null);
                moviesAdapter.notifyItemInserted(initArraylist.size() - 1);

                    handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        initArraylist.remove(initArraylist.size() - 1);
                        moviesAdapter.notifyItemRemoved(initArraylist.size());

                        if (initArraylist.size()<moviesArraylist.size()-1){
                            int start = initArraylist.size();
                            int end = start + 20;

                            if (end > moviesArraylist.size()){
                                end = moviesArraylist.size();
                            }

                            for (int i = start + 1; i < end; i++){
                                if (i>moviesArraylist.size()-1){
                                    Toast.makeText(getApplicationContext(),"到底部了哦",Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                initArraylist.add(moviesArraylist.get(i));
                                moviesAdapter.notifyItemInserted(initArraylist.size());
                            }
                        }

                        moviesAdapter.setLoaded();
                }
                },0);
            }
        });

        //状态码为200时，表示连接服务器成功，广告予以显示。
    }

    public void onFailure(int statusCode){
        Log.d("error","连接服务器失败，状态码是" + statusCode);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 显示menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * menu点击事件刷新
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    MainActivity.mainActivityInstace.finish();
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            });
            Toast.makeText(this,"刷新成功",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_channel) {

            Intent intent = new Intent(this, ChannelActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_search) {

            Intent intent = new Intent(this, TagsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_save) {

            Intent intent = new Intent(this, TasksManagerActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(this,HistoryActivity.class);
            startActivity(intent);
        }
//          else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
