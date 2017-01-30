package com.dmikhov.rssreader.repo;

import com.dmikhov.rssreader.models.RssFeed;

import java.util.List;

import rx.Observable;

/**
 * Created by madless on 29.01.2017.
 */
public interface ILocalRepo extends IExternalRepo {
    Observable<List<RssFeed>> getAllRssFeeds();
    void addRssFeed(RssFeed feed);
    void removeRssFeed(RssFeed feed);
}
