package com.devfun.cartoon.model;

import com.google.gson.annotations.SerializedName;

/**
 * *******************************************
 * * Project cartoon                **
 * * Created by Simon on 6/24/2017.           **
 * * Copyright (c) 2017 by DevFun        **
 * * All rights reserved                    **
 * *******************************************
 */

public class VideoModel extends BaseModel {

    @SerializedName("id")
    private VideoIdModel mVideoId;
    @SerializedName("snippet")
    private SnippetModel mSnippet;

    public SnippetModel getSnippet() {
        return mSnippet;
    }

    public VideoIdModel getVideoId() {
        return mVideoId;
    }
}
