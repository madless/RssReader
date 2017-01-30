package com.dmikhov.rssreader.entities;

import io.realm.RealmObject;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class RssItem extends RealmObject {
    private String title;
    private String image;
    private String description;
    private String link;
    private long date;

    public RssItem() {}

    public RssItem(String title, String image, String description, String link, long date) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.date = date;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "RssItem{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", date=" + date +
                '}';
    }
}
