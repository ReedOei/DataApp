package com.reedoei.data.scraping.scrapers;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */
public class GeneralScraper implements Scraper<String> {
    private final String name;

    public GeneralScraper(final String name) {
        this.name = name.toLowerCase();
    }

    @Override
    public double getScore(final String text) {
        if (text.toLowerCase().contains(name)) {
            return 1.0;
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public Set<String> scrapeData(final String text) {
        return Collections.singleton(text);
    }
}
