package com.itingxi.data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quanhai on 2017/8/16.
 */

public class ChannelDataUtil {
    //初始化栏目数据库
    public static void insetTestData(SQLiteDatabase db){
        if (db == null){
            return;
        }

        List<ContentValues> contentValuesList = new ArrayList<ContentValues>();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_ID,"1");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_NAME,"评剧");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_FATHER,"");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_SONCLASS,"|2|3|");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_ISLAST,"0");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_CLICKED,0);
        contentValuesList.add(contentValues);

        contentValues = new ContentValues();
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_ID,"5");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_NAME,"京剧");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_SONCLASS,"|6|7|8|");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_FATHER,"");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_ISLAST,"0");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_CLICKED,0);
        contentValuesList.add(contentValues);

        contentValues = new ContentValues();
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_ID,"9");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_NAME,"晋剧");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_SONCLASS,"|10|11|12|");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_FATHER,"");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_ISLAST,"0");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_CLICKED,0);
        contentValuesList.add(contentValues);

        contentValues = new ContentValues();
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_ID,"13");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_NAME,"梆子");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_SONCLASS,"|6|7|8|");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_FATHER,"");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_ISLAST,"1");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_CLICKED,0);
        contentValuesList.add(contentValues);

        contentValues = new ContentValues();
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_ID,"14");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_NAME,"越剧");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_SONCLASS,"|15|16|");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_FATHER,"");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_ISLAST,"0");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_CLICKED,0);
        contentValuesList.add(contentValues);

        contentValues = new ContentValues();
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_ID,"17");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_NAME,"豫剧");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_SONCLASS,"|18|19|");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_FATHER,"");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_ISLAST,"0");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_CLICKED,0);
        contentValuesList.add(contentValues);

        contentValues = new ContentValues();
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_ID,"20");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_NAME,"沪剧");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_SONCLASS,"");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_FATHER,"");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_ISLAST,"1");
        contentValues.put(MovieContract.ChannelEntry.CHANNEL_CLICKED,0);
        contentValuesList.add(contentValues);

        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (MovieContract.ChannelEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:contentValuesList){
                db.insert(MovieContract.ChannelEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }
    }
}
