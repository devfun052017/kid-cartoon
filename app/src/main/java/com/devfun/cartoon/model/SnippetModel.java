package com.devfun.cartoon.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * *******************************************
 * * Project cartoon                **
 * * Created by Simon on 6/24/2017.           **
 * * Copyright (c) 2017 by DevFun        **
 * * All rights reserved                    **
 * *******************************************
 */

public class SnippetModel extends BaseModel {
    @SerializedName("title")
    private String mTitle;
    @SerializedName("thumbnails")
    private ThumbnailsModel mThumbnails;
    @SerializedName("channelTitle")
    private String mChannelTitle;

    public String getTitle() {
        if (TextUtils.isEmpty(mTitle))
            return "";
        return mTitle;
    }

    public String getChannelTitle() {
        if (TextUtils.isEmpty(mChannelTitle)) return "";
        return mChannelTitle;
    }

    public ThumbnailsModel getThumbnails() {
        return mThumbnails;
    }
}
