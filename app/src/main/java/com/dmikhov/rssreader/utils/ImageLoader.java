package com.dmikhov.rssreader.utils;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import static com.dmikhov.rssreader.utils.Const.TAG;
/**
 * Created by dmikhov on 27.01.2017.
 */
public class ImageLoader {

    public static void loadImage(String url, ImageView view) {
        Log.d(TAG, "loadImage: " + url);
        if(url != null && view != null && !url.isEmpty()) {
            Glide.with(view.getContext())
                    .load(url)
                    .into(view);
        }
    }
}
