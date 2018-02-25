package com.reedoei.data.scraping.scrapers;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */

public class AnyScraper implements Scraper<String> {
    @Override
    public double getScore(final String text) {
        return 0;
    }

    @NonNull
    @Override
    public Set<String> scrapeData(final String text) {
        return Collections.singleton(text);
    }
}
