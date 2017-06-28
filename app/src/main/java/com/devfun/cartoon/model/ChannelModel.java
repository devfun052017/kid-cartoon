package com.devfun.cartoon.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * *******************************************
 * * Created by Simon on 6/23/2017.           **
 * * Copyright (c) 2017                     **
 * * All rights reserved                    **
 * *******************************************
 */

public class ChannelModel extends BaseModel {

    @SerializedName("id")
    String mId;
    @SerializedName("name")
    String mName;
    @SerializedName("image")
    String mImage;


    public String getId() {
        return mId;
    }

    public String getName() {
        if (TextUtils.isEmpty(mName)) return "";
        return mName;
    }

    public String getImage() {
        return mImage;
    }
}
