package com.dmikhov.rssreader.repo.abs;

import com.dmikhov.rssreader.entities.RssFeed;

/**
 * Created by dmikhov on 30.01.2017.
 */
public interface IUpdatableRepo {
    void addRssFeed(RssFeed feed);
}
