package com.dmikhov.rssreader.sections.main;

import com.dmikhov.rssreader.entities.RssFeed;
import com.dmikhov.rssreader.mvp.BasePresenter;
import com.dmikhov.rssreader.repo.RepositoryManager;
import com.dmikhov.rssreader.sections.main.abs.IMainActivityView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by madless on 29.01.2017.
 */
public class MainActivityPresenter extends BasePresenter {

    private IMainActivityView view;
    private List<RssFeed> feeds;

    public void onStart(final IMainActivityView view) {
        this.view = view;
        if (feeds == null) {
            RepositoryManager.get().getAllRssFeeds()
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
        if(isRssExistsAlready(url)) {
            view.onRssAlreadyExists();
        } else {
            RepositoryManager.get()
                    .getRssFeedByUrl(url)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<RssFeed>() {
                        @Override
                        public void call(RssFeed feed) {
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
}
