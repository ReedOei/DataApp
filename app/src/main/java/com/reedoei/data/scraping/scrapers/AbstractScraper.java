package com.reedoei.data.scraping.scrapers;

import android.support.annotation.NonNull;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */
public abstract class AbstractScraper<T> {
    private final List<String> keywords = new ArrayList<>();

    protected AbstractScraper(List<String> keywords) {
        for (final String keyword : keywords) {
            if (KeywordLists.lists.containsKey(keyword)) {
                this.keywords.addAll(KeywordLists.lists.get(keyword));
            }

            this.keywords.add(keyword.toLowerCase());
        }
    }

    protected double getKeywordScore(final String text) {
        double total = 0;

        for (final String keyword : keywords) {
            if (text.toLowerCase().contains(keyword)) {
                total += 1;
            }
        }

        return total;
    }

    public abstract double getScore(final String text);

    @NonNull
    public abstract List<T> scrapeData(final String text);
}
