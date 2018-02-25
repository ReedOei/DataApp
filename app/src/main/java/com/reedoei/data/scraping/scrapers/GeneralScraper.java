package com.reedoei.data.scraping.scrapers;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */
public class GeneralScraper extends AbstractScraper<String> {
    private final String name;

    public GeneralScraper(final String name, final List<String> keywords) {
        super(keywords);
        this.name = name.toLowerCase();
    }

    @Override
    public double getScore(final String text) {
        double total = getKeywordScore(text);

        if (text.toLowerCase().contains(name)) {
            total += 1.0;
        }

        return total;
    }

    @NonNull
    @Override
    public Set<String> scrapeData(final String text) {
        return Collections.singleton(text);
    }
}
