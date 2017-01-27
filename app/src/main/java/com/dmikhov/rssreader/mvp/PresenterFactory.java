package com.dmikhov.rssreader.mvp;

import android.support.annotation.NonNull;

/**
 * Created by dmikhov on 27.01.2017.
 */
public interface PresenterFactory<T extends BasePresenter> {
    @NonNull
    T createPresenter();
}
