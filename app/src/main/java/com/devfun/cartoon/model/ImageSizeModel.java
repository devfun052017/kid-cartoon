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

public class ImageSizeModel extends BaseModel {
    @SerializedName("url")
    private String mUrl;
    @SerializedName("width")
    private int mWidth;
    @SerializedName("height")
    private int mHeight;

    public int getHeight() {
        return mHeight;
    }

    public int getWidth() {
        return mWidth;
    }

    public String getUrl() {
        return mUrl;
    }
}
