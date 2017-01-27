package com.dmikhov.rssreader.mvp;

import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class PresenterCache {
    private static PresenterCache instance = null;

    private SimpleArrayMap<String, BasePresenter> presenters;

    private PresenterCache() {}

    public static PresenterCache get() {
        if (instance == null) {
            instance = new PresenterCache();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public final <T extends BasePresenter> T getPresenter(String who, PresenterFactory<T> presenterFactory) {
        if (presenters == null) {
            presenters = new SimpleArrayMap<>();
        }
        T p = null;
        try {
            p = (T) presenters.get(who);
        } catch (ClassCastException e) {
            Log.w("PresenterActivity", "Duplicate Presenter " +
                    "tag identified: " + who + ". This could " +
                    "cause issues with state.");
        }
        if (p == null) {
            p = presenterFactory.createPresenter();
            presenters.put(who, p);
        }
        return p;
    }

    public final void removePresenter(String who) {
        if (presenters != null) {
            presenters.remove(who);
        }
    }
}
