package com.reedoei.data.scraping.scrapers;

import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */

public interface Scraper<T> {
    double getScore(final String text);
    Set<T> scrapeData(final String text);
}
