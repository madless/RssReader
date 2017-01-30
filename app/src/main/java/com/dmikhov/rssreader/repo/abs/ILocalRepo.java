package com.dmikhov.rssreader.repo.abs;

import com.dmikhov.rssreader.entities.RssFeed;

import java.util.List;

import rx.Observable;

/**
 * Created by madless on 29.01.2017.
 */
public interface ILocalRepo extends IExternalRepo {
    Observable<List<RssFeed>> getAllRssFeeds();
    void removeRssFeed(RssFeed feed);
}
