package com.dmikhov.rssreader.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by madless on 28.01.2017.
 */
public class NonSwipableViewPager extends ViewPager {
    public NonSwipableViewPager(Context context) {
        super(context);
    }

    public NonSwipableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
