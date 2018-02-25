package com.reedoei.data.scraping.scrapers;

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

    @Override
    public Set<String> scrapeData(final String text) {
        return Collections.singleton(text);
    }
}
