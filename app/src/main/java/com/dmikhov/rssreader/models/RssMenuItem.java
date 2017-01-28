package com.dmikhov.rssreader.models;

/**
 * Created by madless on 28.01.2017.
 */
public class RssMenuItem {
    private String title;
    private boolean isRemovable;

    public RssMenuItem(String title) {
        this.title = title;
        this.isRemovable = true;
    }

    public RssMenuItem(String title, boolean isRemovable) {
        this.title = title;
        this.isRemovable = isRemovable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRemovable() {
        return isRemovable;
    }

    public void setRemovable(boolean removable) {
        isRemovable = removable;
    }

    @Override
    public String toString() {
        return "RssMenuItem{" +
                "title='" + title + '\'' +
                ", isRemovable=" + isRemovable +
                '}';
    }
}
