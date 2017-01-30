package com.dmikhov.rssreader.repo;

import android.text.TextUtils;

import com.dmikhov.rssreader.entities.RssFeed;
import com.dmikhov.rssreader.entities.RssItem;
import com.dmikhov.rssreader.repo.abs.IExternalRepo;
import com.dmikhov.rssreader.utils.Const;
import com.dmikhov.rssreader.utils.DateHelper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.RealmList;
import rx.Observable;
import rx.Subscriber;

import static com.dmikhov.rssreader.utils.Const.CONNECTION_CONNECT_TIMEOUT;
import static com.dmikhov.rssreader.utils.Const.CONNECTION_READ_TIMEOUT;
import static com.dmikhov.rssreader.utils.Const.RSS_XML_TAG_ITEM;

/**
 * Created by dmikhov on 30.01.2017.
 */
public class NetRepositoryUrlConnection implements IExternalRepo {

    private static final String IMAGE_PATTERN = "<(/)?img[^>]*>";
    private static final String IMAGE_SRC_PATTERN = "src=\".*?\"";
    private static final String IMAGE_SRC_DELIM_PATTERN = "\"";

    @Override
    public Observable<RssFeed> getRssFeedByUrl(final String url) {
        return Observable.create(new Observable.OnSubscribe<RssFeed>() {
            @Override
            public void call(Subscriber<? super RssFeed> subscriber) {
                InputStream input = null;
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setReadTimeout(CONNECTION_READ_TIMEOUT);
                    connection.setConnectTimeout(CONNECTION_CONNECT_TIMEOUT);
                    connection.setRequestMethod(Const.CONNECTION_GET);
                    connection.setDoInput(true);
                    connection.connect();
                    if (connection.getResponseCode() != Const.CONNECTION_SUCCESS_RESPONSE_CODE) {
                        connection.disconnect();
                    }
                    input = connection.getInputStream();
                    RssFeed feed = parseXML(url, input, connection.getContentEncoding());
                    subscriber.onNext(feed);
                    subscriber.onCompleted();
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        if(input != null) {
                            input.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    subscriber.onError(e);
                }
            }
        });

    }

    public RssFeed parseXML(String url, InputStream input, String encoding) throws XmlPullParserException {
        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(input, encoding);
        RealmList<RssItem> items = new RealmList<>();
        String channelTitle = null;
        try {
            Stack<String> tags = new Stack<>();
            int eventType = parser.getEventType();
            String text = null;
            String title = null;
            String link = null;
            String description = null;
            long date = 0;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tags.push(name);
                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals(Const.RSS_XML_TAG_TITLE)) {
                            if(tags.contains(RSS_XML_TAG_ITEM)) {
                                title = text;
                            } else {
                                channelTitle = text;
                            }
                        } else if (name.equals(Const.RSS_XML_TAG_LINK)) {
                            link = text;
                        } else if (name.equals(Const.RSS_XML_TAG_DESCRIPTION)) {
                            description = text;
                        } else if (name.equals(Const.RSS_XML_TAG_DATE)) {
                            try {
                                date = DateHelper.getConvertedDate(text);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if (TextUtils.equals(Const.RSS_XML_TAG_ITEM, tags.pop())) {
                            Matcher m = Pattern.compile(IMAGE_SRC_PATTERN).matcher(description);
                            String imgUrl = null;
                            if(m.find()) {
                                String imgSrc = m.group();
                                imgUrl = imgSrc.split(IMAGE_SRC_DELIM_PATTERN)[1];
                            }
                            description = description.replaceAll(IMAGE_PATTERN, "");
                            RssItem item = new RssItem(title, imgUrl, description, link, date);
                            items.add(item);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        RssFeed feed = new RssFeed(channelTitle, url, items);
        return feed;
    }
}
