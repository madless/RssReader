package com.dmikhov.rssreader.sections.rss;

import com.dmikhov.rssreader.models.RssItem;

import java.util.List;

/**
 * Created by dmikhov on 27.01.2017.
 */
public interface IRssView {
    void onRssLoadingStarted();
    void onRssDataLoaded(List<RssItem> items);
    void onRssRefreshingStarted();
    void onRssDataRefreshed(List<RssItem> items);
}
