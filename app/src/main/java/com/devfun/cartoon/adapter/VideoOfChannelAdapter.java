package com.devfun.cartoon.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.devfun.cartoon.R;
import com.devfun.cartoon.model.VideoModel;
import com.devfun.cartoon.utils.AppUtils;

/**
 * *******************************************
 * * Project cartoon                **
 * * Created by Simon on 6/25/2017.           **
 * * Copyright (c) 2017 by DevFun        **
 * * All rights reserved                    **
 * *******************************************
 */

public class VideoOfChannelAdapter extends BaseRecyclerAdapter<VideoModel> {

    @Override
    public int getViewId() {
        return R.layout.adapter_item_video;
    }

    @Override
    public BaseViewHolder getViewHolder(View view) {
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindView(BaseViewHolder holder, VideoModel model) {
        if (holder instanceof VideoViewHolder) {
            VideoViewHolder fHolder = (VideoViewHolder) holder;
            AppUtils.getInstance().loadThumbnails(fHolder.mImageView, model.getSnippet().getThumbnails());
            fHolder.mTextView.setText(model.getSnippet().getTitle());
        }
    }

    private static class VideoViewHolder extends BaseViewHolder {
        ImageView mImageView;
        TextView mTextView;

        VideoViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.adapterItemVideo_image);
            //mImageView.getLayoutParams().height = (int) (260 * itemView.getContext().getResources().getDisplayMetrics().density);//px
            mTextView = (TextView) itemView.findViewById(R.id.adapterItemVideo_textView_videoName);
        }
    }
}
