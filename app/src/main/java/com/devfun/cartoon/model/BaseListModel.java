package com.devfun.cartoon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * *******************************************
 * * Project cartoon                **
 * * Created by Simon on 6/24/2017.           **
 * * Copyright (c) 2017 by DevFun        **
 * * All rights reserved                    **
 * *******************************************
 */

public class BaseListModel<T> extends BaseResponseModel {

    @SerializedName("items")
    private List<T> mItems;

    public List<T> getItems() {
        return mItems;
    }
}
