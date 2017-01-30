package com.dmikhov.rssreader.repo;

import com.dmikhov.rssreader.entities.RssFeed;
import com.dmikhov.rssreader.entities.RssItem;
import com.dmikhov.rssreader.repo.abs.IExternalRepo;
import com.dmikhov.rssreader.utils.Const;
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

import static com.dmikhov.rssreader.utils.Const.RSS_XML_TAG_DATE;
import static com.dmikhov.rssreader.utils.Const.RSS_XML_TAG_DESCRIPTION;
import static com.dmikhov.rssreader.utils.Const.RSS_XML_TAG_ITEM;
import static com.dmikhov.rssreader.utils.Const.RSS_XML_TAG_LINK;
import static com.dmikhov.rssreader.utils.Const.RSS_XML_TAG_TITLE;

/**
 * Created by madless on 29.01.2017.
 */
public class NetRepositoryJsoup implements IExternalRepo {

    private static final String IMAGE_PATTERN = "<(/)?img[^>]*>";

    @Override
    public Observable<RssFeed> getRssFeedByUrl(final String url) {
        return Observable.create(new Observable.OnSubscribe<RssFeed>() {
            @Override
            public void call(Subscriber<? super RssFeed> subscriber) {
                Document document = null;
                try {
                    document = Jsoup.connect(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements items = document.select(RSS_XML_TAG_ITEM);
                RealmList<RssItem> rssItemList = new RealmList<>();
                for (int i = 0; i < items.size(); i++) {
                    String itemCode = items.get(i).html();
                    Document itemDoc = Jsoup.parse(itemCode, url, Parser.xmlParser());
                    String title = itemDoc.select(RSS_XML_TAG_TITLE).text();
                    String link = itemDoc.select(RSS_XML_TAG_LINK).text();
                    String description = itemDoc.select(RSS_XML_TAG_DESCRIPTION).text();
                    String pubDate = itemDoc.select(RSS_XML_TAG_DATE).text();
                    long date = 0;
                    try {
                        date = DateHelper.getConvertedDate(pubDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Document descriptionDoc = Jsoup.parseBodyFragment(description);
                    String img = descriptionDoc.select(Const.RSS_XML_TAG_IMG).attr(Const.RSS_XML_ATTR_SRC);

                    description = description.replaceAll(IMAGE_PATTERN, "");
                    RssItem rssItem = new RssItem(title, img, description, link, date);
                    rssItemList.add(rssItem);
                }
                String channelHtml = document.select(Const.RSS_XML_TAG_CHANNEL).html();
                String feedTitle = Jsoup.parseBodyFragment(channelHtml).select(RSS_XML_TAG_TITLE).first().text();
                RssFeed feed = new RssFeed(feedTitle, url, rssItemList);
                subscriber.onNext(feed);
                subscriber.onCompleted();
            }});
    }
}
