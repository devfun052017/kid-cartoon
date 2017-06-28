package com.devfun.cartoon.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devfun.cartoon.R;
import com.devfun.cartoon.model.ChannelModel;

/**
 * *******************************************
 * * Created by Simon on 6/23/2017.           **
 * * Copyright (c) 2017                     **
 * * All rights reserved                    **
 * *******************************************
 */

public class ChannelAdapter extends
        BaseRecyclerAdapter<ChannelModel> {

    @Override
    public int getViewId() {
        return R.layout.adapter_item_channel;
    }

    @Override
    public ChannelAdapter.ChannelViewHolder getViewHolder(View view) {
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindView(BaseViewHolder holder, ChannelModel model) {
        if(holder instanceof ChannelViewHolder) {
            ChannelViewHolder fHolder = (ChannelViewHolder) holder;
            Glide.with(holder.itemView.getContext())
                    .load(model.getImage())
                    .into(fHolder.mImageView);
            fHolder.mName.setText(model.getName());
        }
    }


    private static class ChannelViewHolder extends BaseViewHolder {
        TextView mName;
        ImageView mImageView;

        ChannelViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.adapterItemChannel_channelName);
            mImageView = (ImageView) itemView.findViewById(R.id.adapterItemChannel_image);
        }
    }
}
