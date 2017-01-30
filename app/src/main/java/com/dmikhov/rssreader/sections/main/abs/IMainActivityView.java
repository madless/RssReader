package com.dmikhov.rssreader.sections.main.abs;

import com.dmikhov.rssreader.entities.RssFeed;

import java.util.List;

/**
 * Created by madless on 29.01.2017.
 */
public interface IMainActivityView {
    void onRssFeedsLoaded(List<RssFeed> feeds);
    void onFeedAdded(RssFeed feed);
    void onRssAlreadyExists();
    void onRssLoadingError();
    void onRssLoadingStarted();
}
