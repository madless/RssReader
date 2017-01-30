package com.dmikhov.rssreader.sections.main;

import android.util.Log;

import com.dmikhov.rssreader.entities.RssFeed;
import com.dmikhov.rssreader.mvp.BasePresenter;
import com.dmikhov.rssreader.repo.RepositoryManager;
import com.dmikhov.rssreader.sections.main.abs.IMainActivityView;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by madless on 29.01.2017.
 */
public class MainActivityPresenter extends BasePresenter {
    private static final String TAG = "MainActivityPresenter";
    
    private IMainActivityView view;
    private List<RssFeed> feeds;
    private Subscription allRssFeedsSubscription;
    private Subscription rssFeedByUrlSubscription;

    public void onStart(final IMainActivityView view) {
        Log.d(TAG, "onStart: feeds: " + feeds + ", allRssFeedsSubscription: " + allRssFeedsSubscription);
        this.view = view;
        if (allRssFeedsSubscription == null && feeds == null) {
            view.onRssLoadingStarted();
            allRssFeedsSubscription = RepositoryManager.get().getAllRssFeeds()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<RssFeed>>() {
                        @Override
                        public void call(List<RssFeed> rssFeeds) {
                            feeds = rssFeeds;
                            view.onRssFeedsLoaded(feeds);
                        }
                    });
        } else {
            view.onRssFeedsLoaded(feeds);
        }
    }

    public void addRssFeed(String url) {
        Log.d(TAG, "addRssFeed: ");
        if(isRssExistsAlready(url)) {
            view.onRssAlreadyExists();
        } else {
            view.onRssLoadingStarted();
            rssFeedByUrlSubscription = RepositoryManager.get()
                    .getRssFeedByUrl(url)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<RssFeed>() {
                        @Override
                        public void call(RssFeed feed) {
                            Log.d(TAG, "feed loaded: " + feed);
                            feeds.add(feed);
                            view.onFeedAdded(feed);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                            view.onRssLoadingError();
                        }
                    });
        }
    }

    public void removeRssFeed(int position) {
        RssFeed feed = feeds.remove(position);
        RepositoryManager.get().removeRssFeed(feed);
    }

    private boolean isRssExistsAlready(String url) {
        for (int i = 0; i < feeds.size(); i++) {
            if(feeds.get(i).getUrl().equals(url)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        if(allRssFeedsSubscription != null && !allRssFeedsSubscription.isUnsubscribed()) {
            allRssFeedsSubscription.unsubscribe();
        }
        if(rssFeedByUrlSubscription != null && !rssFeedByUrlSubscription.isUnsubscribed()) {
            rssFeedByUrlSubscription.unsubscribe();
        }
        super.onDestroy();
    }
}
