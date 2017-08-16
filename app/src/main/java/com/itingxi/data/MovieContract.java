package com.itingxi.data;


import android.provider.BaseColumns;

/**
 * Created by quanhai on 2017/8/14.
 * 要约:用于存放数据库表名,列名.
 */

public class MovieContract {
    //视频合约
    public static final class MovieEntry implements BaseColumns{
        public static final String TABLE_NAME = "movie_data";
        public static final String MOVIE_ID = "movie_id";
        public static final String MOVIE_TITLE = "movie_title";
        public static final String MOVIE_JUMU = "movie_jumu";
        public static final String MOVIE_PLAYER = "movie_player";
        public static final String MOVIE_CLICK = "movie_click";
        public static final String MOVIE_PLAY_ID = "movie_play_id";
        public static final String MOVIE_PLAY_URL = "movie_play_url";
        public static final String MOVIE_GOOD = "movie_good";
        public static final String MOVIE_TAGS = "movie_tags";
        public static final String MOVIE_LENGTH = "movie_length";
        public static final String MOVIE_IMAGE = "movie_image";
        public static final String MOVIE_TIMESTAMP = "movie_timestamp";
    }

    //栏目合约
    public static final class ChannelEntry implements BaseColumns{
        public static final String TABLE_NAME = "channel_data";
        public static final String CHANNEL_ID = "class_id";
        public static final String CHANNEL_SONCLASS = "class_sonClass";
        public static final String CHANNEL_NAME = "class_name";
        public static final String CHANNEL_FATHER = "class_father";
        public static final String CHANNEL_ISLAST = "class_islast";
        public static final String CHANNEL_CLICKED = "class_cliced";
    }
}
