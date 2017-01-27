package com.dmikhov.rssreader.ui.rss;

import com.dmikhov.rssreader.models.RssItem;
import com.dmikhov.rssreader.mvp.BasePresenter;
import com.dmikhov.rssreader.utils.MockData;

import java.util.List;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class RssFragmentPresenter extends BasePresenter {

    private List<RssItem> rssItems;
    private IRssView view;

    public RssFragmentPresenter(IRssView view) {
        this.view = view;
    }

    public void onStart() {
        view.onRssLoadingStarted();
        if(rssItems == null) {
            rssItems = MockData.getRssItems();
            view.onRssDataLoaded(rssItems);
        } else {
            view.onRssDataLoaded(rssItems);
        }
    }

    public void updateData() {
        view.onRssRefreshingStarted();
        rssItems = MockData.getRssItemsUpdated();
        view.onRssDataRefreshed(rssItems);
    }
}
