package com.itingxi.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by quanhai on 16/7/7.
 */
public class Movies implements Parcelable {

    private String movie_id;//标题
    private String movie_title;//标题
    private String movie_jumu;
    private String movie_player;
    private String movie_click;
    private String movie_play_id;
    private String movie_play_url;
    private String movie_good;
    private String movie_tags;
    private String movie_image;
    private String movie_length;

    public Movies(String movie_title){
        setMovie_title(movie_title);
    }

    public Movies(String movie_id,String movie_title,String movie_jumu,String movie_player,String movie_click,String movie_play_id,
                  String movie_play_url,String movie_good,String movie_tags,String movie_image,String movie_length) {
        setMovie_click(movie_click);
        setMovie_good(movie_good);
        setMovie_id(movie_id);
        setMovie_jumu(movie_jumu);
        setMovie_play_id(movie_play_id);
        setMovie_play_url(movie_play_url);
        setMovie_tags(movie_tags);
        setMovie_title(movie_title);
        setMovie_player(movie_player);
        setMovie_image(movie_image);
        setMovie_length(movie_length)  ;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "movie_id='" + movie_id + '\'' +
                ", movie_title='" + movie_title + '\'' +
                ", movie_jumu='" + movie_jumu + '\'' +
                ", movie_player='" + movie_player + '\'' +
                ", movie_click='" + movie_click + '\'' +
                ", movie_play_id='" + movie_play_id + '\'' +
                ", movie_play_url='" + movie_play_url + '\'' +
                ", movie_good='" + movie_good + '\'' +
                ", movie_tags='" + movie_tags + '\'' +
                ", movie_image='" + movie_image + '\'' +
                '}';
    }
    public String getMovie_length() {
        return movie_length;
    }

    public void setMovie_length(String movie_length) {
        this.movie_length = movie_length;
    }

    public String getMovie_image() {
        return movie_image;
    }

    public void setMovie_image(String movie_image) {
        this.movie_image = movie_image;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_jumu() {
        return movie_jumu;
    }

    public void setMovie_jumu(String movie_jumu) {
        this.movie_jumu = movie_jumu;
    }

    public String getMovie_player() {
        return movie_player;
    }

    public void setMovie_player(String movie_player) {
        this.movie_player = movie_player;
    }

    public String getMovie_click() {
        return movie_click;
    }

    public void setMovie_click(String movie_click) {
        this.movie_click = movie_click;
    }

    public String getMovie_play_id() {
        return movie_play_id;
    }

    public void setMovie_play_id(String movie_play_id) {
        this.movie_play_id = movie_play_id;
    }

    public String getMovie_play_url() {
        return movie_play_url;
    }

    public void setMovie_play_url(String movie_play_url) {
        this.movie_play_url = movie_play_url;
    }

    public String getMovie_good() {
        return movie_good;
    }

    public void setMovie_good(String movie_good) {
        this.movie_good = movie_good;
    }

    public String getMovie_tags() {
        return movie_tags;
    }

    public void setMovie_tags(String movie_tags) {
        this.movie_tags = movie_tags;
    }

    protected Movies(Parcel in) {
        movie_id = in.readString();
        movie_title = in.readString();
        movie_jumu = in.readString();
        movie_player = in.readString();
        movie_click = in.readString();
        movie_play_id = in.readString();
        movie_play_url = in.readString();
        movie_good = in.readString();
        movie_tags = in.readString();
        movie_image = in.readString();
        movie_length = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movie_id);
        parcel.writeString(movie_title);
        parcel.writeString(movie_jumu);
        parcel.writeString(movie_player);
        parcel.writeString(movie_click);
        parcel.writeString(movie_play_id);
        parcel.writeString(movie_play_url);
        parcel.writeString(movie_good);
        parcel.writeString(movie_tags);
        parcel.writeString(movie_image);
        parcel.writeString(movie_length);
    }
}
