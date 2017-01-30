package com.dmikhov.rssreader.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class ImageLoader {

    public static void loadImage(String url, ImageView view, RequestListener<String, GlideDrawable> listener) {
        Glide.with(view.getContext())
                .load(url)
                .centerCrop()
                .listener(listener)
                .into(view);
    }
}
