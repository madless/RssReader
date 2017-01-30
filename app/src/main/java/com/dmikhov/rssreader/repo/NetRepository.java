package com.dmikhov.rssreader.repo;

import android.util.Log;

import com.dmikhov.rssreader.models.RssFeed;
import com.dmikhov.rssreader.models.RssItem;
import com.dmikhov.rssreader.utils.DateHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;

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
                    Document itemDoc = Jsoup.parse(itemCode, url, Parser.xmlParser()); //Jsoup.parseBodyFragment(itemCode);
                    String title = itemDoc.select("title").text();
                    String link = itemDoc.select("link").text();
                    String description = itemDoc.select("description").text();
                    String pubDate = itemDoc.select("pubDate").text();
                    long date = 0;
                    try {
                        date = DateHelper.getConvertedDate(pubDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Document descriptionDoc = Jsoup.parseBodyFragment(description);
                    String img = descriptionDoc.select("img").attr("src");

                    description = description.replaceAll(IMAGE_PATTERN, "");
                    Log.d(TAG, "item link: " + link);
                    RssItem rssItem = new RssItem(title, img, description, link, date);
                    rssItemList.add(rssItem);
                }
                String channelHtml = document.select("channel").html();
                String feedTitle = Jsoup.parseBodyFragment(channelHtml).select("title").first().text();
                RssFeed feed = new RssFeed(feedTitle, url, rssItemList);
                subscriber.onNext(feed);
                subscriber.onCompleted();
            }});
    }
}
