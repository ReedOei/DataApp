package com.reedoei.data.scraping.scrapers;

import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by roei on 2/24/18.
 */

public class AgeScraper implements Scraper<Integer> {
    private static final Pattern agePattern = Pattern.compile("([0-9]+)");

    @Override
    public double getScore(final String text) {
        double total = 0;

        if (text.toLowerCase().contains("age")) {
            total += 1;
        }

        return total;
    }

    @NonNull
    @Override
    public Set<Integer> scrapeData(final String text) {
        final Set<Integer> result = new HashSet<>();

        final List<Map<Integer, String>> matches = ScraperUtils.findAll(agePattern, text);

        for (final Map<Integer, String> match : matches) {
            for (final String val : match.values()) {
                try {
                    result.add(Integer.parseInt(val));
                } catch (NumberFormatException ignored) {}
            }
        }

        return result;
    }
}
