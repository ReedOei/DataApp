package com.reedoei.data.scraping.scrapers;

import android.support.annotation.NonNull;

import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */

public interface Scraper<T> {
    double getScore(final String text);

    @NonNull
    Set<T> scrapeData(final String text);
}
