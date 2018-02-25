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

public class PhoneNumberScraper implements Scraper<String> {
    private static final Pattern pattern = Pattern.compile("([0-9]{3}-[0-9]{3}-[0-9]{4})");

    @Override
    public double getScore(String text) {
        double total = 0;

        if (text.toLowerCase().contains("number")) {
            total += 0.2;
        }

        if (text.toLowerCase().contains("phone")) {
            total += 0.5;
        }

        if (text.toLowerCase().contains("phone number")) {
            total += 1.0;
        }

        return total;
    }

    @NonNull
    @Override
    public Set<String> scrapeData(String text) {
        return new HashSet<>(ScraperUtils.findAllStrings(pattern, text));
    }
}
