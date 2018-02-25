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
    private final String dataTypeStr;
    private final AbstractScraper<T> scraper;

    public static Query<String> any() {
        return new Query<>(DataType.ANY, "", new AnyScraper(Collections.<String>emptyList()));
    }

    public static Query<Integer> age() {
        return age(Collections.<String>emptyList());
    }

    public static Query<Integer> age(final List<String> keywords) {
        return new Query<>(DataType.AGE, "age", new AgeScraper(keywords));
    }

    public static Query<Date> date() {
        return date(Collections.<String>emptyList());
    }

    public static Query<Date> date(final List<String> keywords) {
        return new Query<>(DataType.DATE, "date", new DateScraper(keywords));
    }

    public static Query<String> general(final String name) {
        return general(name, Collections.<String>emptyList());
    }

    public static Query<String> general(final String name, final List<String> keywords) {
        return new Query<>(DataType.GENERAL, "general", new GeneralScraper(name, keywords));
    }

    public static Query<String> phoneNumber() {
        return phoneNumber(Collections.<String>emptyList());
    }

    public static Query<String> phoneNumber(final List<String> keywords) {
        return new Query<>(DataType.PHONE_NUMBER, "phone-number", new PhoneNumberScraper(keywords));
    }

    public Query(DataType dataType, String dataTypeStr, AbstractScraper<T> scraper) {
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
