package com.reedoei.data.scraping.scrapers;

import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */

public class AgeScraper implements Scraper<Integer> {
    @Override
    public double getScore(String text) {
        double total = 0;

        if (text.contains("age")) {
            total += 1;
        }

        return total;
    }

    @Override
    public Set<Integer> scrapeData(String text) {
        return null;
    }
}
