package com.dmikhov.rssreader.repo;

import android.util.Log;

import com.dmikhov.rssreader.models.RssFeed;
import com.dmikhov.rssreader.models.RssItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import io.realm.RealmList;
import rx.Observable;
import rx.Subscriber;

import static com.dmikhov.rssreader.utils.Const.TAG;

/**
 * Created by madless on 29.01.2017.
 */
public class NetRepository implements IExternalRepo {

    private static final String IMAGE_PATTERN = "<(/)?img[^>]*>";

    @Override
    public Observable<RssFeed> getRssFeedByUrl(final String url) {
        Log.d(TAG, "NetRepository getRssFeedByUrl: " + url);
        return Observable.create(new Observable.OnSubscribe<RssFeed>() {
            @Override
            public void call(Subscriber<? super RssFeed> subscriber) {
                Document document = null;
                try {
                    document = Jsoup.connect(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements items = document.select("item");
                RealmList<RssItem> rssItemList = new RealmList<>();
                for (int i = 0; i < items.size(); i++) {
                    String itemCode = items.get(i).html();
                    Document itemDoc = Jsoup.parseBodyFragment(itemCode);

                    String title = itemDoc.select("title").text();
                    String description = itemDoc.select("description").text();
                    String pubDate = itemDoc.select("pubDate").text();

                    Document descriptionDoc = Jsoup.parseBodyFragment(description);
                    String img = descriptionDoc.select("img").attr("src");

                    description = description.replaceAll(IMAGE_PATTERN, "");
                    RssItem rssItem = new RssItem(title, img, description, 0);
                    rssItemList.add(rssItem);
                }
                String channelHtml = document.select("channel").html();
                String feedTitle = Jsoup.parseBodyFragment(channelHtml).select("title").first().text();
                Log.d(TAG, "feedTitle: " + feedTitle);
                RssFeed feed = new RssFeed(feedTitle, url, rssItemList);
                subscriber.onNext(feed);
                subscriber.onCompleted();
            }});
    }
}
