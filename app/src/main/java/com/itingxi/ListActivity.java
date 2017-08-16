package com.itingxi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.itingxi.utils.ImageUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

/**
 * 用于显示tag列表的界面
 */
public class ListActivity extends BasicActivity{

    private MoviesAdapter moviesAdapter;
    private RecyclerView recyclerView;
    public static  ArrayList moviesArraylist = null;
    private TextView tvEmptyView;
    private Handler handler;
    private LinearLayoutManager mLayoutManager;
    public ArrayList initArraylist;
    public static ListActivity listActivityInstance;
    private int statusCode;//状态码

    private TextView textView;
    private String text_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listActivityInstance = this;

        //图片初始化
        ImageUtils.initUniversalImageLoader(getApplicationContext());

        //找到text Id
        textView = (TextView) findViewById(R.id.textMessage);

        //加载广告

//        if (Application.statusCode){
//            GoogleAD.bannerView(this,R.id.ad_list_banner);
//        }

        Intent intent = getIntent();
        String url = intent.getStringExtra(Application.URL_MESSAGE);//得到URL
        text_message = intent.getStringExtra(Application.TEXT_MESSAGE);//得到text

        String name = "tag";
        ClientUtils.getClientUtils().getNetDate(url,new MoviesAsyncResponseHandler(ListActivity.this,this,name));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("list");
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("list");
        MobclickAgent.onPause(this);
    }

    /**
     * 请求数据成功
     * @param arrayList
     */

    @Override
    public void onSuccess(int statusCode, final ArrayList arrayList) {
        super.onSuccess(statusCode, arrayList);

        this.moviesArraylist = arrayList;

        textView.setText("为您找到 " + text_message + " 的相关视频为 "+ moviesArraylist.size() + " 个");
        Log.d("movisArraylist1", String.valueOf(moviesArraylist.size()));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_item);
        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        handler = new Handler();

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        initArraylist = new ArrayList();

        if (arrayList.size() > 7 ){
            for (int i = 0; i< 7; i++){
                initArraylist.add(arrayList.get(i));
            }
        } else {
            for (int i = 0; i<arrayList.size();i++){
                initArraylist.add(arrayList.get(i));
            }
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
                if ( PlayActivity.playActivityInstance != null){
                    PlayActivity.playActivityInstance.finish();
                }
                Intent intent = new Intent(ListActivity.this,PlayActivity.class);
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
                                if (i >moviesArraylist.size() - 1){
                                    Toast.makeText(getApplicationContext(),"到底部了哦",Toast.LENGTH_SHORT).show();
                                    break;
                                }

                                initArraylist.add(moviesArraylist.get(i));
                                moviesAdapter.notifyItemInserted(moviesArraylist.size());
                            }
                        }
                        moviesAdapter.setLoaded();
                    }
                },0);
            }
        });

    }
}
