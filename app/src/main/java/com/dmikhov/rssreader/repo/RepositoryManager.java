package com.dmikhov.rssreader.repo;

import android.util.Log;

import com.dmikhov.rssreader.models.RssFeed;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

import static com.dmikhov.rssreader.utils.Const.TAG;

/**
 * Created by madless on 29.01.2017.
 */
public class RepositoryManager implements ILocalRepo {

    private LocalRepository localRepository;
    private NetRepository netRepository;
    private static RepositoryManager instance;

    private RepositoryManager() {
        localRepository = new LocalRepository();
        netRepository = new NetRepository();
    }

    public static RepositoryManager get() {
        if(instance == null) {
            instance = new RepositoryManager();
        }
        return instance;
    }

    @Override
    public Observable<List<RssFeed>> getAllRssFeeds() {
        return localRepository.getAllRssFeeds();
    }

    @Override
    public void addRssFeed(RssFeed feed) {
        // TODO: 29.01.2017 remove unsupported
    }

    @Override
    public void removeRssFeed(RssFeed feed) {
        localRepository.removeRssFeed(feed);
    }

    @Override
    public Observable<RssFeed> getRssFeedByUrl(final String url) {
        Log.d(TAG, "RepositoryManager getRssFeedByUrl: " + url);
        return localRepository.getRssFeedByUrl(url).switchIfEmpty(netRepository.getRssFeedByUrl(url).flatMap(new Func1<RssFeed, Observable<RssFeed>>() {
            @Override
            public Observable<RssFeed> call(RssFeed feed) {
                Log.d(TAG, "Local repo is empty");
                localRepository.addRssFeed(feed);
                return localRepository.getRssFeedByUrl(url);
            }}));
    }

    public Observable<RssFeed> updateRssFeed(final String url) {
        return netRepository.getRssFeedByUrl(url).flatMap(new Func1<RssFeed, Observable<RssFeed>>() {
            @Override
            public Observable<RssFeed> call(RssFeed feed) {
                localRepository.addRssFeed(feed);
                return localRepository.getRssFeedByUrl(url);
            }
        });
    }
}
