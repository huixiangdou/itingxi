package com.itingxi;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itingxi.Adapter.MoviesAdapter;
import com.itingxi.Adapter.OnRecyclerViewItemClickListener;
import com.itingxi.async.ClientUtils;
import com.itingxi.async.MoviesAsyncResponseHandler;
import com.itingxi.basic.BasicActivity;
import com.itingxi.data.MovieDbHelper;
import com.itingxi.model.Movies;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用于显示搜索结果的界面
 */
public class SearchActivity extends BasicActivity{
    /*
    搜索页面
     */
    public int i=0;
    private MoviesAdapter moviesAdapter;
    private RecyclerView recyclerView;
    private String button_search_text;
    public static SearchActivity searchActivityInstance;
    private SQLiteDatabase sqLiteDatabase;
    private MovieDbHelper movieDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchActivityInstance = this;

        movieDbHelper = new MovieDbHelper(this);
        sqLiteDatabase = movieDbHelper.getWritableDatabase();

        //搜索结果
        Intent intent = getIntent();
        button_search_text = intent.getStringExtra("button_search_text");

        TextView textView_search = (TextView) findViewById(R.id.textView_search);
        textView_search.setHint(button_search_text);

        //加载movies数据
        String name = "movies";
        String url = Application.getKeywordJson + button_search_text;
        ClientUtils.getClientUtils().getNetDate(url,new MoviesAsyncResponseHandler(SearchActivity.this,this,name));
    }

    /**
     *搜索
     */
    public void button_search(View view){
        EditText editText = (EditText) findViewById(R.id.textView_search);
        button_search_text = editText.getText().toString();

        /**
         *校验输入的搜索关键字
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

        if ( SearchActivity.searchActivityInstance != null){
            SearchActivity.searchActivityInstance.finish();
        }

        if ( TagsActivity.tagsActivityInstance != null){
            TagsActivity.tagsActivityInstance.finish();
        }

        movieDbHelper.addNewSearchKey(sqLiteDatabase,button_search_text);//添加搜索记录

         //搜索内容跳转
        Intent intent = new Intent(this,SearchActivity.class);
        intent.putExtra("button_search_text",button_search_text);
        startActivity(intent);
    }

    public void onResume(){
        super.onResume();
        MobclickAgent.onPageStart("search");
        MobclickAgent.onResume(this);
    }
    public void onPause(){
        super.onPause();
        MobclickAgent.onPageEnd("search");
        MobclickAgent.onPause(this);
    }

    /**
     * 请求movies数据成功
     * @param arrayList
     */
    @Override
    public void onSuccess(int statusCode, ArrayList arrayList) {

        recyclerView = (RecyclerView) findViewById(R.id.recycler_item);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        moviesAdapter = new MoviesAdapter(arrayList);
        recyclerView.setAdapter(moviesAdapter);

        String toastText = "关于 「 " + button_search_text + " 」的相关结果约为 「 " + moviesAdapter.getItemCount() +" 」个";
        Toast.makeText(SearchActivity.this,toastText,Toast.LENGTH_SHORT).show();

        moviesAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object object) {

                Intent intent = new Intent(SearchActivity.this,PlayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("MOVIES", (Movies)object);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
