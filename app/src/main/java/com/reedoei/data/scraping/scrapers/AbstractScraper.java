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

    protected double getKeywordPercentage(final String text) {
        double total = 0;

        for (final String keyword : keywords) {
            if (text.toLowerCase().contains(keyword)) {
                total += 1;
            }
        }

        if (keywords.size() > 0) {
            return total / keywords.size();
        } else {
            return 0.0;
        }
    }

    public double getKeywordScore(final String text) {
        final double keywordFactor = getKeywordFactor(text);
        final double keywordPercentage = getKeywordPercentage(text);

        return keywordFactor * keywordPercentage + keywordFactor; // Add to make sure not 0.
    }

    public double getDatascore(final List<String> potentialData) {
        double parsableCount = 0;

        for (final String d : potentialData) {
            parsableCount += scrapeData(d).size();
        }

        if (potentialData.size() > 0) {
            return parsableCount / potentialData.size();
        } else {
            return 0.0;
        }
    }

    public abstract double getDataFactor(final List<String> potentialData);
    public abstract double getKeywordFactor(final String text);

    public double getScore(final String text, final List<String> potentialData) {
        final double dataFactor = getDataFactor(potentialData);

        return getKeywordScore(text) + getDatascore(potentialData) * dataFactor + dataFactor;
    }

    @NonNull
    public abstract List<T> scrapeData(final String text);
}
