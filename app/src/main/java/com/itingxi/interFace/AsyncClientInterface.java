package com.itingxi.interFace;

import com.itingxi.model.Cntv_video;
import com.itingxi.model.Youku_video;

import java.util.ArrayList;


/**
 * Created by quanhai on 19/10/2016.
 */

public interface AsyncClientInterface {
    void onSuccess(int statusCode,ArrayList arrayList);
    void onSuccess(ArrayList arrayList);
    void onSuccess(String string);
    void onSuccess(Youku_video youku_video);
    void onSuccess(Cntv_video cntv_video);
    void onFailure(int statusCode);
}
