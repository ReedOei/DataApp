package com.reedoei.data.scraping.query;

import com.reedoei.data.data.Data;
import com.reedoei.data.data.DataType;
import com.reedoei.data.scraping.scrapers.AbstractScraper;

import java.util.List;

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
        final List<T> data = scraper.scrapeData(text);
        final double score = scraper.getKeywordFactor(text);

        return new Data<>(dataType, data, text, score);
    }

    public double getScore(final String key, final List<String> potentialData) {
        return scraper.getScore(key, potentialData);
    }
}
