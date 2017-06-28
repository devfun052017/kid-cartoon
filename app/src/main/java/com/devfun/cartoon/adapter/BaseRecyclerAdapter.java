package com.devfun.cartoon.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devfun.cartoon.R;
import com.devfun.cartoon.helper.OnItemClickListener;
import com.devfun.cartoon.model.BaseModel;

import java.util.List;

/**
 * *******************************************
 * * Created by Simon on 6/23/2017.           **
 * * Copyright (c) 2017                     **
 * * All rights reserved                    **
 * *******************************************
 */

public abstract class BaseRecyclerAdapter<T extends BaseModel>
        extends RecyclerView.Adapter<BaseViewHolder> {
    private List<T> mData;
    private OnItemClickListener mListener;
    private int ITEM_TYPE = 1, LOADING_TYPE = 2;
    private boolean mFooter;

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void appendItems(List<T> data) {
        if (data == null) return;
        mData.addAll(data);
        notifyItemRangeChanged(getDataSize(), data.size());
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        if (holder instanceof LoadingViewHolder) {
            //TODO write something
            onBindFooter();
        } else {
            onBindView(holder, mData.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(mData.get(holder.getAdapterPosition()),
                                holder.getAdapterPosition());
                    }
                }
            });
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOADING_TYPE) {
            View fView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_loading_more, parent, false);
            return new LoadingViewHolder(fView);
        }
        View fView = LayoutInflater.from(parent.getContext())
                .inflate(getViewId(), parent, false);
        return getViewHolder(fView);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getDataSize())
            return LOADING_TYPE;
        return ITEM_TYPE;
    }

    @Override
    public int getItemCount() {
        return getDataSize() + (mFooter ? 1 : 0);
    }

    private int getDataSize() {
        return (mData == null ? 0 : mData.size());
    }

    abstract protected int getViewId();

    abstract protected BaseViewHolder getViewHolder(View view);

    abstract protected void onBindView(BaseViewHolder holder, T model);

    //region Footer
    protected void onBindFooter() {
    }

    public void showFooter(boolean isShowFooter) {
        mFooter = isShowFooter;
        if (isShowFooter) {
            notifyItemInserted(getDataSize());
        } else {
            notifyItemRemoved(getDataSize());
        }
    }
    //endregion
}
