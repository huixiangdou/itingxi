package com.itingxi.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.itingxi.Application;
import com.itingxi.model.Movies;

/**
 * Created by quanhai on 2017/8/14.
 */

public class MovieDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = Application.DATABASE_NAME;//数据库名称
    public static final int DATABASE_VERSION = Application.DATABASE_VERSION;//数据库版本号

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表语句
        final String SQL_CREATE_MOVIE_TABLE = "create table " + MovieContract.MovieEntry.TABLE_NAME + "(" +
                MovieContract.MovieEntry._ID + " integer primary key autoincrement," +
                MovieContract.MovieEntry.MOVIE_TITLE   + " VARCHAR NOT NULL, "                 +
                MovieContract.MovieEntry.MOVIE_ID   + " VARCHAR NOT NULL, "                    +
                MovieContract.MovieEntry.MOVIE_PLAY_ID   + " VARCHAR NOT NULL, "               +
                MovieContract.MovieEntry.MOVIE_IMAGE   + " VARCHAR, "                 +
                MovieContract.MovieEntry.MOVIE_JUMU   + " VARCHAR NOT NULL, "                  +
                MovieContract.MovieEntry.MOVIE_PLAY_URL   + " VARCHAR NOT NULL, "              +
                MovieContract.MovieEntry.MOVIE_PLAYER   + " VARCHAR NOT NULL, "                +
                MovieContract.MovieEntry.MOVIE_GOOD   + " VARCHAR NOT NULL, "                  +
                MovieContract.MovieEntry.MOVIE_TAGS   + " VARCHAR NOT NULL, "                  +
                MovieContract.MovieEntry.MOVIE_LENGTH   + " VARCHAR, "                +
                MovieContract.MovieEntry.MOVIE_CLICK   + " VARCHAR NOT NULL, "                 +
                MovieContract.MovieEntry.MOVIE_TIMESTAMP   + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        Log.d("create","create");
//        final String SQL_CREATE_CHANNEL_TABLE = "create table " + MovieContract.ChannelEntry.TABLE_NAME + "(" +
//                MovieContract.ChannelEntry._ID + " integer primary key autoincrement," +
//                MovieContract.ChannelEntry.CHANNEL_ID + " VARCHAR not null," +
//                MovieContract.ChannelEntry.CHANNEL_NAME + " VARCHAR not null," +
//                MovieContract.ChannelEntry.CHANNEL_FATHER + " VARCHAR," +
//                MovieContract.ChannelEntry.CHANNEL_SONCLASS + " VARCHAR," +
//                MovieContract.ChannelEntry.CHANNEL_CLICKED + " integer not null," +
//                MovieContract.ChannelEntry.CHANNEL_ISLAST + " VARCHAR not null" +
//                ");";
        Log.d("create","create");
//        db.execSQL(SQL_CREATE_CHANNEL_TABLE);
        db.execSQL(SQL_CREATE_MOVIE_TABLE);//执行SQL语句,创建数据表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + MovieContract.MovieEntry.TABLE_NAME);//数据表是否存在,存在,删掉
        db.execSQL("drop table if exists " + MovieContract.ChannelEntry.TABLE_NAME);//数据表是否存在,存在,删掉
        onCreate(db);//重新创建数据表
    }

    /**
     * 获取数据库所有内容
     * @return Cursor
     */
    public Cursor getAllHistoryMovies(SQLiteDatabase sqLiteDatabase,Context context){
        Cursor histotyCursor = sqLiteDatabase.query(
                MovieContract.MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MovieContract.MovieEntry.MOVIE_TIMESTAMP + " desc"
        );
        if (histotyCursor.getCount() == 0){
            Toast.makeText(context,"哦哦!您的播放记录为空哦",Toast.LENGTH_SHORT).show();
        }
        return histotyCursor;
    }

    /**
     * 获取数据库所有内容
     * @return Cursor
     */
    public Cursor getAllChannel(SQLiteDatabase sqLiteDatabase, Context context){
        Cursor channelCursor = sqLiteDatabase.query(
                MovieContract.ChannelEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MovieContract.ChannelEntry.CHANNEL_ID + " desc"
        );
        return channelCursor;
    }

    /**
     * 添加视频数据
     * @param movie
     * @return 插入的位置
     */
    public long addNewMovie(Movies movie,SQLiteDatabase sqLiteDatabase){
        Log.d("add",movie.toString());
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.MOVIE_TITLE,movie.getMovie_title());
        contentValues.put(MovieContract.MovieEntry.MOVIE_CLICK,movie.getMovie_click());
        contentValues.put(MovieContract.MovieEntry.MOVIE_GOOD,movie.getMovie_good());
        contentValues.put(MovieContract.MovieEntry.MOVIE_ID,movie.getMovie_id());
        contentValues.put(MovieContract.MovieEntry.MOVIE_IMAGE,movie.getMovie_image());
        contentValues.put(MovieContract.MovieEntry.MOVIE_JUMU,movie.getMovie_jumu());
        contentValues.put(MovieContract.MovieEntry.MOVIE_LENGTH,movie.getMovie_length());
        contentValues.put(MovieContract.MovieEntry.MOVIE_PLAY_ID,movie.getMovie_play_id());
        contentValues.put(MovieContract.MovieEntry.MOVIE_PLAY_URL,movie.getMovie_play_url());
        contentValues.put(MovieContract.MovieEntry.MOVIE_PLAYER,movie.getMovie_player());
        contentValues.put(MovieContract.MovieEntry.MOVIE_TAGS,movie.getMovie_tags());
        return sqLiteDatabase.insert(MovieContract.MovieEntry.TABLE_NAME,null,contentValues);
    }

    /**
     * 删除数据
     */
    public boolean removeHistoryMovie(SQLiteDatabase sqLiteDatabase, String id) {
        // COMPLETED (2) Inside, call mDb.delete to pass in the TABLE_NAME and the condition that WaitlistEntry._ID equals id
        return sqLiteDatabase.delete(MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry.MOVIE_ID + "=" + id, null) > 0;
    }
}
