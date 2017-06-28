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

public class BaseResponseModel extends BaseModel {
    @SerializedName("nextPageToken")
    private String mNextPageToken;
    @SerializedName("error")
    private ErrorModel mError;

    public ErrorModel getError() {
        return mError;
    }

    public String getNextPageToken() {
        return mNextPageToken;
    }
}
