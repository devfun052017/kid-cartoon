package com.devfun.cartoon.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.devfun.cartoon.R;
import com.devfun.cartoon.adapter.VideoPlayerAdapter;
import com.devfun.cartoon.helper.OnItemClickListener;
import com.devfun.cartoon.model.BaseListModel;
import com.devfun.cartoon.model.VideoModel;
import com.devfun.cartoon.network.GenerateService;
import com.devfun.cartoon.network.YoutubeService;

/**
 * *******************************************
 * * Created by Simon on 6/26/2017.           **
 * * Copyright (c) 2017                     **
 * * All rights reserved                    **
 * *******************************************
 */

public class PlaylistOfVideoFragment extends BaseFragment<BaseListModel<VideoModel>> {
    private RecyclerView mRecyclerView;
    private FrameLayout mLoading;
    private String mChannelTitle;
    private VideoPlayerAdapter mAdapter;
    private OnItemClickListener mListener;

    public void setChannelTitle(String channelTitle) {
        if (getView() == null) {
            throw new RuntimeException("Please call it after init view.");
        }
        mChannelTitle = channelTitle;
        searchVideo();
    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    protected void onDataResult(BaseListModel<VideoModel> result) {
        mLoading.setVisibility(View.GONE);
        if (result.getItems().size() == 0) return;
        mAdapter.setData(result.getItems());
        mRecyclerView.scrollToPosition(0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play_list_of_video, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragmentPlayListVideo_recyclerView);
        mLoading = (FrameLayout) view.findViewById(R.id.fragmentPlayListVideo_loading);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mAdapter = new VideoPlayerAdapter();
        mAdapter.setListener(mListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void searchVideo() {
        mLoading.setVisibility(View.VISIBLE);
        request(GenerateService.getInstance()
                .createService(YoutubeService.class)
                .searchVideo(mChannelTitle, null));
    }
}
