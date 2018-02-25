package com.reedoei.data.scraping.query;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.reedoei.data.data.DataType;
import com.reedoei.data.scraping.scrapers.AbstractScraper;
import com.reedoei.data.scraping.scrapers.AgeScraper;
import com.reedoei.data.scraping.scrapers.AnyScraper;
import com.reedoei.data.scraping.scrapers.DateScraper;
import com.reedoei.data.scraping.scrapers.GeneralScraper;
import com.reedoei.data.scraping.scrapers.PhoneNumberScraper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * Created by roei on 2/24/18.
 */

public class QueryBuilder<T, S extends AbstractScraper<T>> {
    public static QueryBuilder<String, AnyScraper> any() {
        return new QueryBuilder<>(DataType.ANY, AnyScraper::new);
    }

    public static QueryBuilder<Integer, AgeScraper> age() {
        return new QueryBuilder<>(DataType.AGE, AgeScraper::new);
    }

    public static QueryBuilder<Date, DateScraper> date() {
        return new QueryBuilder<>(DataType.DATE, DateScraper::new);
    }

    public static QueryBuilder<String, GeneralScraper> general() {
        return new QueryBuilder<>(DataType.GENERAL, GeneralScraper::new);
    }

    public static QueryBuilder<String, PhoneNumberScraper> phoneNumber() {
        return new QueryBuilder<>(DataType.PHONE_NUMBER, PhoneNumberScraper::new);
    }

    private DataType dataType;
    private Function<List<String>, S> constructor;
    private List<String> keywords = new ArrayList<>();

    public QueryBuilder(final DataType dataType, final Function<List<String>, S> constructor) {
        this.dataType = dataType;
        this.constructor = constructor;
    }

    public QueryBuilder<T, S> keyword(final String keyword) {
        keywords.add(keyword);
        return this;
    }

    public QueryBuilder<T, S> keywords(final Collection<String> keywords) {
        this.keywords.addAll(keywords);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Query<T> build() {
        return new Query<>(dataType, constructor.apply(keywords));
    }
}
