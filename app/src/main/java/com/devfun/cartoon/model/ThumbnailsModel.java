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

public class ThumbnailsModel extends BaseModel {
    @SerializedName("default")
    private ImageSizeModel mDefault;
    @SerializedName("medium")
    private ImageSizeModel mMedium;
    @SerializedName("high")
    private ImageSizeModel mHigh;

    public ImageSizeModel getDefault() {
        return mDefault;
    }

    public ImageSizeModel getHigh() {
        return mHigh;
    }

    public ImageSizeModel getMedium() {
        return mMedium;
    }
}
