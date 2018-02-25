package com.reedoei.data.scraping.query;

import com.reedoei.data.scraping.scrapers.AnyScraper;
import com.reedoei.data.scraping.scrapers.Scraper;

import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */

public class Query<T> {
    private final DataType dataType;
    private final String dataTypeStr;
    private final Scraper<T> scraper;

    public static Query<String> getAll() {
        return new Query<>(DataType.ANY, "", new AnyScraper());
    }

    public Query(DataType dataType, String dataTypeStr, Scraper<T> scraper) {
        this.dataType = dataType;
        this.dataTypeStr = dataTypeStr;
        this.scraper = scraper;
    }

    public DataType getDataType() {
        return dataType;
    }

    public Data<T> scrape(String text) {
        final Set<T> data = scraper.scrapeData(text);
        final double score = scraper.getScore(text);

        return new Data<>(dataType, dataTypeStr, data, text, score);
    }
}
