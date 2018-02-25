package com.reedoei.data.scraping.scrapers;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by roei on 2/24/18.
 */

public class AgeScraper extends AbstractScraper<Integer> {
    private static final Pattern agePattern = Pattern.compile("([0-9]+)");

    public AgeScraper(List<String> keywords) {
        super(keywords);
    }

    @Override
    public double getDataFactor(List<String> potentialData) {
        return 0.1; // There are tons of numbers, so this isn't that important to us.
    }

    @Override
    public double getKeywordFactor(final String text) {
        double total = 1.0;

        if (text.toLowerCase().contains("age")) {
            total += 1.5;
        }

        return total;
    }

    @NonNull
    @Override
    public List<Integer> scrapeData(final String text) {
        final List<Integer> result = new ArrayList<>();

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
