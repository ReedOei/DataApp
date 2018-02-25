package com.reedoei.data.scraping.scrapers;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by roei on 2/24/18.
 */

public class DateScraper implements Scraper<Date> {
    private static final Pattern datePattern = Pattern.compile("([0-9]+)-");

    @Override
    public double getScore(final String text) {
        return 0;
    }

    @NonNull
    @Override
    public Set<Date> scrapeData(String text) {
        return null;
    }
}
