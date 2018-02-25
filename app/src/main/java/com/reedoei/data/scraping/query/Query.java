package com.reedoei.data.scraping.query;

import com.reedoei.data.scraping.scrapers.AgeScraper;
import com.reedoei.data.scraping.scrapers.AnyScraper;
import com.reedoei.data.scraping.scrapers.DateScraper;
import com.reedoei.data.scraping.scrapers.GeneralScraper;
import com.reedoei.data.scraping.scrapers.PhoneNumberScraper;
import com.reedoei.data.scraping.scrapers.AbstractScraper;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */

public class Query<T> {
    private final DataType dataType;
    private final AbstractScraper<T> scraper;

    public Query(DataType dataType, AbstractScraper<T> scraper) {
        this.dataType = dataType;
        this.scraper = scraper;
    }

    public DataType getDataType() {
        return dataType;
    }

    public Data<T> scrape(String text) {
        final Set<T> data = scraper.scrapeData(text);
        final double score = scraper.getScore(text);

        return new Data<>(dataType, data, text, score);
    }

    public double getScore(final String text) {
        return scraper.getScore(text);
    }
}
