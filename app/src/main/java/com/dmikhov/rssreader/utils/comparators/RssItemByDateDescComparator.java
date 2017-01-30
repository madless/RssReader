package com.dmikhov.rssreader.utils.comparators;

import com.dmikhov.rssreader.models.RssItem;

import java.util.Comparator;

/**
 * Created by dmikhov on 30.01.2017.
 */
public class RssItemByDateDescComparator implements Comparator<RssItem> {
    @Override
    public int compare(RssItem item1, RssItem item2) {
        long d1 = item1.getDate();
        long d2 = item2.getDate();
        long diff = d2 - d1;
        return diff > 0 ? 1 : diff < 0 ? -1 : 0;
    }
}
