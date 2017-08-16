package com.itingxi.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.itingxi.Application;

/**
 * Created by quanhai on 2017/8/16.
 */

public class ChnannelDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = Application.DATABASE_NAME;
    public static final int DATABASE_VERSION = Application.DATABASE_VERSION;

    public ChnannelDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_CHANNEL_TABLE = "create table " + MovieContract.ChannelEntry.CHANNEL_NAME + "(" +
                MovieContract.ChannelEntry._ID + " integer primary key autoincrement," +
                MovieContract.ChannelEntry.CHANNEL_ID + " VARCHAR not null," +
                MovieContract.ChannelEntry.CHANNEL_NAME + " VARCHAR not null," +
                MovieContract.ChannelEntry.CHANNEL_FATHER + " VARCHAR," +
                MovieContract.ChannelEntry.CHANNEL_SONCLASS + " VARCHAR," +
                MovieContract.ChannelEntry.CHANNEL_CLICKED + " integer not null," +
                MovieContract.ChannelEntry.CHANNEL_ISLAST + " VARCHAR not null" +
                ");";
        Log.d("create","create");
        db.execSQL(SQL_CREATE_CHANNEL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + MovieContract.ChannelEntry.TABLE_NAME);//数据表是否存在,存在,删掉
        onCreate(db);//重新创建数据表
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
}
