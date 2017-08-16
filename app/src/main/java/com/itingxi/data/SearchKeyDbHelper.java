package com.itingxi.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.itingxi.Application;

/**
 * Created by quanhai on 2017/8/16.
 */

public class SearchKeyDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = Application.DATABASE_NAME;
    private static final int DATABASE_VERSION = Application.DATABASE_VERSION;
    public SearchKeyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SEARCHKEY_TABLE = "create table " + MovieContract.SearchKey.TABLE_NAME + "(" +
                MovieContract.SearchKey._ID + "integer primary key autoincrement," +
                MovieContract.SearchKey.SEARCH_STRING + " varchar not null,"  +
                MovieContract.SearchKey.SEARCH_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_SEARCHKEY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + MovieContract.MovieEntry.TABLE_NAME);//数据表是否存在,存在,删掉
        onCreate(db);//重新创建数据表
    }

    /**
     * 获取数据库所有内容
     * @return Cursor
     */
    public Cursor getAllSearchKey(SQLiteDatabase sqLiteDatabase, Context context){
        Cursor searchKeyCursor = sqLiteDatabase.query(
                MovieContract.SearchKey.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MovieContract.SearchKey.SEARCH_TIMESTAMP + " desc"
        );
        if (searchKeyCursor.getCount() == 0){
            Toast.makeText(context,"哦哦!您的播放记录为空哦",Toast.LENGTH_SHORT).show();
        }
        return searchKeyCursor;
    }

    /**
     * 添加视频数据
     * @param searchKey
     * @return 插入的位置
     */
    public long addNewSearchKey(String searchKey, SQLiteDatabase sqLiteDatabase){
        Log.d("add",searchKey);
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.SearchKey.SEARCH_STRING,searchKey);
        return sqLiteDatabase.insert(MovieContract.SearchKey.TABLE_NAME,null,contentValues);
    }
}
