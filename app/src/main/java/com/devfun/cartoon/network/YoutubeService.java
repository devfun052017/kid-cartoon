package com.devfun.cartoon.network;

import com.devfun.cartoon.model.BaseListModel;
import com.devfun.cartoon.model.VideoModel;
import com.devfun.cartoon.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * *******************************************
 * * Project cartoon                **
 * * Created by Simon on 6/24/2017.           **
 * * Copyright (c) 2017 by DevFun        **
 * * All rights reserved                    **
 * *******************************************
 */

public interface YoutubeService {
    String GET_ITEMS_OF_CHANNEL = "search?key=" + Constants.API_KEY + "&part=snippet,id&order=date&maxResults=" + Constants.ITEM_OF_PAGE;
    String SEARCH_VIDEO = "search?key=" + Constants.API_KEY + "&part=snippet,id&type=video&order=date&maxResults=" + Constants.ITEM_OF_PAGE;

    @GET(GET_ITEMS_OF_CHANNEL)
    Call<BaseListModel<VideoModel>> getItemsOfChannel(@Query("channelId") String channelId,
                                                      @Query("pageToken") String pageToken);

    @GET(SEARCH_VIDEO)
    Call<BaseListModel<VideoModel>> searchVideo(@Query("q") String key,
                                                @Query("pageToken") String pageToken);
}
