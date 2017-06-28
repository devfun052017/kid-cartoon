package com.devfun.cartoon.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * *******************************************
 * * Project cartoon                **
 * * Created by Simon on 6/24/2017.           **
 * * Copyright (c) 2017 by DevFun        **
 * * All rights reserved                    **
 * *******************************************
 */
public class GenerateService {
    private static final GenerateService sInstance = new GenerateService();
    private static final Retrofit sRetrofit;
    private static final OkHttpClient sOkHttpClient;
    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";

    static {
        sOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor()).build();
        sRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(sOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private GenerateService() {

    }

    public static GenerateService getInstance() {
        return sInstance;
    }

    public <C> C createService(Class<C> cls) {
        return sRetrofit.create(cls);
    }
}
