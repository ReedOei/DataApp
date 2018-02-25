package com.reedoei.data.scraping.scrapers;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by roei on 2/24/18.
 */

public class PhoneNumberScraper extends AbstractScraper<String> {
    private static final Pattern pattern = Pattern.compile("([0-9]{3}-[0-9]{3}-[0-9]{4})");

    public PhoneNumberScraper(List<String> keywords) {
        super(keywords);
    }

    @Override
    public double getDataFactor(List<String> potentialData) {
        return 1.0;
    }

    @Override
    public double getKeywordFactor(String text) {
        double total = 1.0;

        if (text.toLowerCase().contains("number") || text.contains("#")) {
            total += 0.2;
        }

        if (text.toLowerCase().contains("phone")) {
            total += 0.5;
        }

        if (text.toLowerCase().contains("phone number") || text.contains("phone #")) {
            total += 1.0;
        }

        return total;
    }

    @NonNull
    @Override
    public List<String> scrapeData(String text) {
        return ScraperUtils.findAllStrings(pattern, text);
    }
}
