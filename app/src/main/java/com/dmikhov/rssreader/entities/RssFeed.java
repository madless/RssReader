package com.dmikhov.rssreader.entities;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by madless on 29.01.2017.
 */
public class RssFeed extends RealmObject {
    @PrimaryKey
    private String url;
    private String name;
    private RealmList<RssItem> rssItems;

    public RssFeed() {}

    public RssFeed(String name, String url, RealmList<RssItem> rssItems) {
        this.name = name;
        this.url = url;
        this.rssItems = rssItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<RssItem> getRssItems() {
        return rssItems;
    }

    public void setRssItems(RealmList<RssItem> rssItems) {
        this.rssItems = rssItems;
    }

    @Override
    public String toString() {
        return "RssFeed{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
