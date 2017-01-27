package com.dmikhov.rssreader.models;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class RssItem {
    private String title;
    private String image;
    private String description;
    private long date;

    public RssItem(String title, String image, String description, long date) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.date = date;
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

    @Override
    public String toString() {
        return "RssItem{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
