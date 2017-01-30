package com.dmikhov.rssreader.utils;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class Const {
    public static final String TAG = "dmikhov_rss";

    public static final String RSS_FRAGMENT_PRESENTER = "RSS_FRAGMENT_PRESENTER";
    public static final String MAIN_ACTIVITY_PRESENTER = "MAIN_ACTIVITY_PRESENTER";

    public static final String EXTRA_DATA_LINK = "EXTRA_DATA_LINK";
    public static final String EXTRA_DATA_RSS_URL = "EXTRA_DATA_RSS_URL";

    public static final String CONNECTION_GET = "GET";
    public static final int CONNECTION_READ_TIMEOUT = 8000;
    public static final int CONNECTION_CONNECT_TIMEOUT = 16000;
    public static final int CONNECTION_SUCCESS_RESPONSE_CODE = 200;

    public static final String RSS_XML_TAG_CHANNEL = "channel";
    public static final String RSS_XML_TAG_ITEM = "item";
    public static final String RSS_XML_TAG_DATE = "pubDate";
    public static final String RSS_XML_TAG_LINK = "link";
    public static final String RSS_XML_TAG_TITLE = "title";
    public static final String RSS_XML_TAG_DESCRIPTION = "description";
    public static final String RSS_XML_TAG_IMG = "img";
    public static final String RSS_XML_ATTR_SRC = "src";
}
