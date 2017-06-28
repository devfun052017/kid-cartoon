package com.devfun.cartoon.helper;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * *******************************************
 * * Created by Simon on 6/23/2017.           **
 * * Copyright (c) 2017                     **
 * * All rights reserved                    **
 * *******************************************
 */

public class ItemDecorationAlbumColumns extends RecyclerView.ItemDecoration {
    private int mSizeGridSpacingPx;
    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public ItemDecorationAlbumColumns(int gridSpacingPx) {
        mSizeGridSpacingPx = gridSpacingPx;
    }

    public ItemDecorationAlbumColumns(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (spanCount == 0) {
            outRect.left = mSizeGridSpacingPx;
            outRect.right = mSizeGridSpacingPx;
            outRect.bottom = mSizeGridSpacingPx;
            outRect.top = mSizeGridSpacingPx;
        } else {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column
            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
        calculateHeight(view, parent.getMeasuredWidth() / 2);
    }

    private void calculateHeight(View view, int width) {
        if (width <= 0) return;
        int height;
        if (includeEdge) {
            height = width - (2 + spanCount) * spacing;
        } else {
            height = width - spanCount * spacing;
        }
        view.getLayoutParams().height = height;
    }
}