package com.dmikhov.rssreader.repo;

import com.dmikhov.rssreader.models.RssFeed;

import rx.Observable;

/**
 * Created by madless on 29.01.2017.
 */
public interface IExternalRepo {
    Observable<RssFeed> getRssFeedByUrl(String url);
}
