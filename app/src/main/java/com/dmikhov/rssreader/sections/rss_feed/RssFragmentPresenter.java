package com.dmikhov.rssreader.sections.rss_feed;

import android.util.Log;

import com.dmikhov.rssreader.entities.RssFeed;
import com.dmikhov.rssreader.entities.RssItem;
import com.dmikhov.rssreader.mvp.BasePresenter;
import com.dmikhov.rssreader.repo.RepositoryManager;
import com.dmikhov.rssreader.sections.rss_feed.abs.IRssView;
import com.dmikhov.rssreader.utils.comparators.RssItemByDateAscComparator;
import com.dmikhov.rssreader.utils.comparators.RssItemByDateDescComparator;

import java.util.Collections;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class RssFragmentPresenter extends BasePresenter {
    private static final String TAG = "RssFragmentPresenter";

    private List<RssItem> rssItems;
    private String rssUrl;
    private IRssView view;

    public void setView(IRssView view) {
        this.view = view;
    }

    public void onStart(final String rssUrl) {
        this.rssUrl = rssUrl;
        view.onRssLoadingStarted();
        Log.d(TAG, "onStart: " + rssItems);
        if (rssItems == null) {
            RepositoryManager.get().getRssFeedByUrl(rssUrl).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<RssFeed>() {
                        @Override
                        public void call(RssFeed feed) {
                            rssItems = feed.getRssItems();
                            view.onRssDataLoaded(rssItems);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    });
        } else {
            view.onRssDataLoaded(rssItems);
        }
    }

    public void updateData() {
        view.onRssRefreshingStarted();
        RepositoryManager.get().updateRssFeed(rssUrl).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RssFeed>() {
                    @Override
                    public void call(RssFeed feed) {
                        rssItems = feed.getRssItems();
                        view.onRssDataRefreshed(rssItems);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.onLoadingError();
                    }
                });

    }

    public void sortByDateAsc() {
        Collections.sort(rssItems, new RssItemByDateAscComparator());
        view.onRssDataLoaded(rssItems);
    }

    public void sortByDateDesc() {
        Collections.sort(rssItems, new RssItemByDateDescComparator());
        view.onRssDataLoaded(rssItems);
    }
}
