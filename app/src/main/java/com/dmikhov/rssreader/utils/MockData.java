package com.dmikhov.rssreader.utils;

import com.dmikhov.rssreader.models.RssItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class MockData {

    public static List<RssItem> getRssItems() {
        List<RssItem> rssItems = new ArrayList<>();
        RssItem item1 = new RssItem("Title 1", "http://i.kinja-img.com/gawker-media/image/upload/s--D341uu7---/c_scale,f_auto,fl_progressive,q_80,w_800/vzihm90dwjntaagdujkn.png", "Description 1", 1485532282167L);
        RssItem item2 = new RssItem("Title 2", "http://i.kinja-img.com/gawker-media/image/upload/s--D341uu7---/c_scale,f_auto,fl_progressive,q_80,w_800/vzihm90dwjntaagdujkn.png", "Description 2", 1485532282167L);
        RssItem item3 = new RssItem("Title 3", "http://i.kinja-img.com/gawker-media/image/upload/s--D341uu7---/c_scale,f_auto,fl_progressive,q_80,w_800/vzihm90dwjntaagdujkn.png", "Description 3", 1485532282167L);
        RssItem item4 = new RssItem("Title 4", "", "Description 4", 1485431282167L);
        rssItems.add(item1);
        rssItems.add(item2);
        rssItems.add(item3);
        rssItems.add(item4);
        return rssItems;
    }

    public static List<RssItem> getRssItemsUpdated() {
        List<RssItem> rssItems = new ArrayList<>();
        RssItem item1 = new RssItem("Title 1", "http://i.kinja-img.com/gawker-media/image/upload/s--D341uu7---/c_scale,f_auto,fl_progressive,q_80,w_800/vzihm90dwjntaagdujkn.png", "Description 1", 1485532282167L);
        RssItem item2 = new RssItem("Title 2", "http://i.kinja-img.com/gawker-media/image/upload/s--D341uu7---/c_scale,f_auto,fl_progressive,q_80,w_800/vzihm90dwjntaagdujkn.png", "Description 2", 1485532282167L);
        RssItem item3 = new RssItem("Title 3", "http://i.kinja-img.com/gawker-media/image/upload/s--D341uu7---/c_scale,f_auto,fl_progressive,q_80,w_800/vzihm90dwjntaagdujkn.png", "Description 3", 1485532282167L);
        RssItem item4 = new RssItem("Title 4", "", "Description 4", 1485431282167L);
        RssItem item5 = new RssItem("Title 5", "", "Description 5", 1465431284167L);
        rssItems.add(item1);
        rssItems.add(item2);
        rssItems.add(item3);
        rssItems.add(item4);
        rssItems.add(item5);
        return rssItems;
    }

}
