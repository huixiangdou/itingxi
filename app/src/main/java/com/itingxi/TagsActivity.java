package com.itingxi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itingxi.Adapter.TagAdapter;
import com.itingxi.model.Tag;
import com.itingxi.utils.HttpUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义tag界面
 */
public class TagsActivity extends AppCompatActivity {
    private ArrayList<Tag> tagList;
    private RecyclerView mRecyclerView;
    private TagAdapter mAdapter;
    public static TagsActivity tagsActivityInstance;

    private Handler getTagsHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String jsonData = (String) msg.obj;
            try {
                new JSONObject(jsonData);
                JSONObject jsonArray = new JSONObject(jsonData);
                for (int i = jsonArray.length() - 1; i >= 0; i--) {
                    String tagName = jsonArray.getString(Integer.toString(i));
                    tagList.add(new Tag(tagName));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        tagsActivityInstance = this;

        //广告
//        if (Application.statusCode){
//            GoogleAD.bannerView(this,R.id.ad_tags_banner);
//        }

        mRecyclerView = (RecyclerView)findViewById(R.id.movies_tags_recycler_view);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        tagList = new ArrayList<Tag>();
        mAdapter = new TagAdapter(tagList);
        mRecyclerView.setAdapter(mAdapter);

        String GET_PL_URL = "http://www.itingxi.com/e/extend/app2/setTagsJson.php";
        HttpUtils.getNewsJson(GET_PL_URL, getTagsHandler);

        mAdapter.setOnItemClickListener(new TagAdapter.OnRecyclerViewItemClickListener(){
            @Override
            public void onItemClick(View view , String data){
                Intent intent = new Intent(TagsActivity.this,ListActivity.class);
                String url = Application.getTagsJson + data;
                intent.putExtra(Application.URL_MESSAGE,url);
                intent.putExtra(Application.TEXT_MESSAGE,data);
                startActivity(intent);
                Toast.makeText(TagsActivity.this,data,Toast.LENGTH_SHORT).show();
            }
        });

    }
    /*
    搜索
     */
    public void button_search(View view){
        EditText editText = (EditText) findViewById(R.id.textView_search);
        String button_search_text = editText.getText().toString();

        /**
         * 校验输入的搜索关键字
         */
        String regEx = "[^\\-\u4E00-\u9FA5]";
        Pattern p   =   Pattern.compile(regEx);//pattern模式
        Matcher m   =   p.matcher(button_search_text);//进行匹配
        button_search_text = m.replaceAll("").trim();

        /**
         *搜索内容判断
         */
        if(button_search_text == null || button_search_text.trim().isEmpty()){
            Toast.makeText(getApplicationContext(),"非法的关键词或关键词为空",Toast.LENGTH_SHORT).show();
            return;
        }else if(button_search_text.length()>32){
            Toast.makeText(getApplicationContext(),"搜索关键字过长,请缩短再试",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this,SearchActivity.class);
        intent.putExtra("button_search_text",button_search_text);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("tags");
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("tags");
        MobclickAgent.onPause(this);
    }
}
