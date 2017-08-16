package com.itingxi;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.itingxi.Adapter.MovieHistoryAdapter;
import com.itingxi.Adapter.OnRecyclerViewItemClickListener;
import com.itingxi.data.MovieDbHelper;
import com.itingxi.model.Movies;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity{
    private MovieHistoryAdapter moviesAdapter;
    private MovieDbHelper movieDbHelper;
    private SQLiteDatabase movieDb;
    private Movies movies;

    @BindView(R.id.recycler_item)
    RecyclerView movieRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器

        movieDbHelper = new MovieDbHelper(this);//新建一个数据库帮助类
        movieDb = movieDbHelper.getWritableDatabase(); //获取数据库类
        Cursor cursor = movieDbHelper.getAllHistoryMovies(movieDb,this);//获取光标

        moviesAdapter = new MovieHistoryAdapter(this,cursor);//创建适配器
        movieRecyclerView.setAdapter(moviesAdapter);//设置适配器

        moviesAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object object) {
                Log.d("onClick","onclick");
                Intent intent = new Intent(HistoryActivity.this,PlayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("MOVIES", (Movies)object);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });//设置点击事件

        moviesAdapter.swapCursor(movieDbHelper.getAllHistoryMovies(movieDb,this));//更新列表UI


        //横扫列表删除
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            //横扫
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                movies = (Movies) viewHolder.itemView.getTag(R.id.tag_movie);//获取此ITEM的视频Id
                movieDbHelper.removeHistoryMovie(movieDb,movies.getMovie_id());//删除数据

                moviesAdapter.swapCursor(movieDbHelper.getAllHistoryMovies(movieDb,HistoryActivity.this));//刷新列表
            }
        }).attachToRecyclerView(movieRecyclerView);
    }
}
