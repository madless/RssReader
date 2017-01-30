package com.dmikhov.rssreader.sections.rss;

import com.dmikhov.rssreader.models.RssFeed;
import com.dmikhov.rssreader.models.RssItem;
import com.dmikhov.rssreader.mvp.BasePresenter;
import com.dmikhov.rssreader.repo.RepositoryManager;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class RssFragmentPresenter extends BasePresenter {

    private List<RssItem> rssItems;
    private String rssUrl;
    private IRssView view;

    public RssFragmentPresenter(IRssView view) {
        this.view = view;
    }

    public void onStart(final String rssUrl) {
        this.rssUrl = rssUrl;
        view.onRssLoadingStarted();
        if (rssItems == null) {
            RepositoryManager.get().getRssFeedByUrl(rssUrl).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<RssFeed>() {
                        @Override
                        public void call(RssFeed feed) {
                            rssItems = feed.getRssItems();
                            view.onRssDataLoaded(rssItems);
                        }
                    });
        } else {
            view.onRssDataLoaded(rssItems);
        }
    }

    public void updateData() {
        view.onRssRefreshingStarted();
        RepositoryManager.get().getRssFeedByUrl(rssUrl).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RssFeed>() {
                    @Override
                    public void call(RssFeed feed) {
                        rssItems = feed.getRssItems();
                        view.onRssDataRefreshed(rssItems);
                    }
                });

    }
}
