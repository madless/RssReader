package com.dmikhov.rssreader.sections.main_navigation;

import com.dmikhov.rssreader.models.RssFeed;

import java.util.List;

/**
 * Created by madless on 29.01.2017.
 */
public interface IMainActivityView {
    void onRssFeedsLoaded(List<RssFeed> feeds);
    void onFeedAdded(RssFeed feed);
    void onRssAlreadyExists();
    void onRssLoadingError();
}
