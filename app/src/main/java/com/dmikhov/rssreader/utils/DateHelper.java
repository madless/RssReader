package com.dmikhov.rssreader.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dmikhov on 30.01.2017.
 */
public class DateHelper {
    private static final String RSS_FEED_DATE_PATTERN = "\\d{2}\\s\\w*\\s\\d{4}\\s\\d{2}:\\d{2}:\\d{2}";

    public static long getConvertedDate(String date) throws ParseException { // e.g. "Sun, 29 Jan 2017 22:50:42 GMT"
        Pattern pattern = Pattern.compile(RSS_FEED_DATE_PATTERN);
        Matcher matcher = pattern.matcher(date);
        String dateInProperFormat = null;
        if(matcher.find()) {
            dateInProperFormat = matcher.group();
        }
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.US);
        return format.parse(dateInProperFormat).getTime();
    }
}
