package com.cwenhui.utils;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cwenhui.test.R;

import java.io.File;


public class DataBindingProperty {

    @BindingAdapter({"imageUrl"})
    public static void setImageScr(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    @BindingAdapter({"fileUrl"})
    public static void setImageSrc(ImageView imageView, String url) {
        File file = new File(url);
        int imageWidth = imageView.getMeasuredWidth();
        Glide.with(imageView.getContext())
                .load(file)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }

    @BindingAdapter({"imageId"})
    public static void setImageId(ImageView imageView, int imageId) {
        imageView.setImageResource(imageId);
    }

    @BindingAdapter({"isRefresh"})
    public static void isRefresh(SwipeRefreshLayout swipeRefreshLayout, boolean isLoad) {
        swipeRefreshLayout.setRefreshing(isLoad);
    }
}
