package com.dmikhov.rssreader.repo;

import android.util.Log;

import com.dmikhov.rssreader.entities.RssFeed;
import com.dmikhov.rssreader.repo.abs.ILocalRepo;
import com.dmikhov.rssreader.repo.abs.IUpdatableRepo;

import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;

import static com.dmikhov.rssreader.utils.Const.TAG;

/**
 * Created by madless on 29.01.2017.
 */
public class LocalRepository implements ILocalRepo, IUpdatableRepo {

    @Override
    public Observable<List<RssFeed>> getAllRssFeeds() {
        return Observable.create(new Observable.OnSubscribe<List<RssFeed>>() {
            @Override
            public void call(Subscriber<? super List<RssFeed>> subscriber) {
                Realm realm = Realm.getDefaultInstance();
                List<RssFeed> feeds = realm.where(RssFeed.class).findAll();
                subscriber.onNext(realm.copyFromRealm(feeds));
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<RssFeed> getRssFeedByUrl(final String url) {
        Log.d(TAG, "LocalRepository getRssFeedByUrl: " + url);
        return Observable.create(new Observable.OnSubscribe<RssFeed>() {
            @Override
            public void call(Subscriber<? super RssFeed> subscriber) {
                Realm realm = Realm.getDefaultInstance();
                RssFeed feed = realm.where(RssFeed.class).equalTo("url", url).findFirst();
                if(feed != null) {
                    subscriber.onNext(realm.copyFromRealm(feed));
                    subscriber.onCompleted();
                } else {
                    subscriber.onCompleted();
                }
            }
        });
    }

    @Override
    public void addRssFeed(final RssFeed feed) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(feed);
            }
        });
    }

    @Override
    public void removeRssFeed(final RssFeed feed) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(RssFeed.class).equalTo("url", feed.getUrl()).findFirst().removeFromRealm();
            }
        });
    }
}
