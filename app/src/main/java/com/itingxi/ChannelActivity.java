package com.itingxi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.headerfooter.songhang.library.SmartRecyclerAdapter;
import com.itingxi.Adapter.ChannelAdapter;
import com.itingxi.Adapter.OnRecyclerViewItemClickListener;
import com.itingxi.async.ChannelAsyncResponseHandler;
import com.itingxi.async.ClientUtils;
import com.itingxi.basic.BasicActivity;
import com.itingxi.model.Channel;

import java.util.ArrayList;

/**
 * 栏目导航界面
 */
public class ChannelActivity extends BasicActivity{

    private RecyclerView channelRecyclerView;
    private ChannelAdapter channelAdapter;
    private int statusCode;//返回状态码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        /**
         * 请求栏目数据
         */
        String url = "http://www.itingxi.com/e/extend/app2/getChannel.php";
        String name = "channels";

        ClientUtils.getClientUtils().getNetDate(url,new ChannelAsyncResponseHandler(ChannelActivity.this,this,name));
    }

    @Override
    public void onSuccess(int statusCode, ArrayList arrayList) {

        channelRecyclerView = (RecyclerView) findViewById(R.id.list_channel);
        channelRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL));
        channelAdapter = new ChannelAdapter(arrayList);
        SmartRecyclerAdapter smartRecyclerAdapter = new SmartRecyclerAdapter(channelAdapter);
        channelRecyclerView.setAdapter(smartRecyclerAdapter);
        channelAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object object) {
                Channel channel = (Channel)object;
                Intent intent = new Intent(ChannelActivity.this,ListActivity.class);
                String url = null;

                //如果sonclass为空，传入本身ID；
                if (channel.getSonClass().length()>2){
                    String sonClass = channel.getSonClass();
                    String sonId = sonClass.substring(1,sonClass.length()-1).replace("|",",");

                    url = Application.getChannelMovies + sonId;
                    intent.putExtra(Application.URL_MESSAGE,url);
                    intent.putExtra(Application.TEXT_MESSAGE,channel.getClassName());
                    startActivity(intent);
                    Toast.makeText(ChannelActivity.this,channel.getClassName(),Toast.LENGTH_SHORT).show();
                }else {

                    String classId = channel.getClassId();

                        url = Application.getChannelMovies + classId;
                        intent.putExtra(Application.URL_MESSAGE,url);
                        intent.putExtra(Application.TEXT_MESSAGE,channel.getClassName());
                        startActivity(intent);
                        Toast.makeText(ChannelActivity.this,channel.getClassName(),Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}