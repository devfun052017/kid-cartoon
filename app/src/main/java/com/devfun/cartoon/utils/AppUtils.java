package com.devfun.cartoon.utils;

import android.content.Context;
import android.widget.ImageView;

import com.devfun.cartoon.model.ChannelModel;
import com.devfun.cartoon.model.ThumbnailsModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * *******************************************
 * * Created by Simon on 6/23/2017.           **
 * * Copyright (c) 2017                     **
 * * All rights reserved                    **
 * *******************************************
 */

public class AppUtils {
    private static final AppUtils ourInstance = new AppUtils();

    public static AppUtils getInstance() {
        return ourInstance;
    }

    private AppUtils() {
    }

    public List<ChannelModel> loadJSONFromAsset(Context context) {
        try {
            InputStream is = context.getAssets().open("channel.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            Type fType = new TypeToken<List<ChannelModel>>() {
            }.getType();
            return new Gson().fromJson(json, fType);
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void loadThumbnails(ImageView imageView, ThumbnailsModel thumbnails) {
        if (thumbnails == null) return;
        Context fContext = imageView.getContext();
        if (thumbnails.getDefault() != null) {
            loadImage(fContext, thumbnails.getDefault().getUrl(), imageView);
        }
        if (thumbnails.getMedium() != null) {
            loadImage(fContext, thumbnails.getMedium().getUrl(), imageView);
        }
        if (thumbnails.getHigh() != null) {
            loadImage(fContext, thumbnails.getHigh().getUrl(), imageView);
        }
    }

    private void loadImage(Context context, String url, ImageView imageView) {
        Picasso.with(context)
                .load(url)
                .into(imageView);
    }
}
