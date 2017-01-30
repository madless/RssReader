package com.dmikhov.rssreader.repo;

import android.util.Log;

import com.dmikhov.rssreader.entities.RssFeed;
import com.dmikhov.rssreader.repo.abs.IExternalRepo;
import com.dmikhov.rssreader.repo.abs.ILocalRepo;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by madless on 29.01.2017.
 */
public class RepositoryManager implements ILocalRepo {
    private static final String TAG = "RepositoryManager";

    private LocalRepository localRepository;
    private IExternalRepo netRepository;
    private static RepositoryManager instance;

    private RepositoryManager() {
        localRepository = new LocalRepository();
        netRepository = new NetRepositoryUrlConnection();
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
    public void removeRssFeed(RssFeed feed) {
        localRepository.removeRssFeed(feed);
    }

    @Override
    public Observable<RssFeed> getRssFeedByUrl(final String url) {
        return localRepository.getRssFeedByUrl(url).switchIfEmpty(netRepository.getRssFeedByUrl(url).flatMap(new Func1<RssFeed, Observable<RssFeed>>() {
            @Override
            public Observable<RssFeed> call(RssFeed feed) {
                Log.d(TAG, "localRepository is empty");
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
