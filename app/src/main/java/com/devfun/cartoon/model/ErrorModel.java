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

public class ErrorModel extends BaseModel{
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        if (TextUtils.isEmpty(message))
            return "Đã xảy ra lỗi trong quá trình thực thi. Vui lòng thử lại.";
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
