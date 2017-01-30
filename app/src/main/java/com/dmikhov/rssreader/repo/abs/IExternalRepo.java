package com.dmikhov.rssreader.repo.abs;

import com.dmikhov.rssreader.entities.RssFeed;

import rx.Observable;

/**
 * Created by madless on 29.01.2017.
 */
public interface IExternalRepo {
    Observable<RssFeed> getRssFeedByUrl(String url);
}
