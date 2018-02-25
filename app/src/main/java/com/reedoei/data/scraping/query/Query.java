package com.reedoei.data.scraping.query;

import com.reedoei.data.scraping.scrapers.AgeScraper;
import com.reedoei.data.scraping.scrapers.AnyScraper;
import com.reedoei.data.scraping.scrapers.DateScraper;
import com.reedoei.data.scraping.scrapers.Scraper;

import java.util.Date;
import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */

public class Query<T> {
    private final DataType dataType;
    private final String dataTypeStr;
    private final Scraper<T> scraper;

    public static Query<String> any() {
        return new Query<>(DataType.ANY, "", new AnyScraper());
    }

    public static Query<Integer> age() {
        return new Query<>(DataType.AGE, "age", new AgeScraper());
    }

    public static Query<Date> date() {
        return new Query<>(DataType.DATE, "date", new DateScraper());
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

    public double getScore(final String text) {
        return scraper.getScore(text);
    }
}
