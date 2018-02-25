package com.reedoei.data.scraping.query;

import android.support.annotation.NonNull;

import com.reedoei.data.scraping.scraped.Table;

import org.apache.commons.math3.util.Pair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */

public class TableScraper implements Queryable {
    private final Document doc;

    public TableScraper(final Document doc) {
        this.doc = doc;
    }

    public final Set<Table> scrape() {
        final Set<Table> result = new HashSet<>();

        for (final Element element : doc.getElementsByTag("table")) {
            result.add(new Table(element));
        }

        return result;
    }

    @NonNull
    @Override
    public <T> DataSet<T> handleQuery(Query<T> query) {
        DataSet<T> dataSet = DataSet.empty();

        for (final Table table : scrape()) {
            final DataSet<T> tempDataSet = table.handleQuery(query);

            if (tempDataSet.getScore() > dataSet.getScore()) {
                dataSet = tempDataSet;
            }
        }

        return dataSet;
    }

    @NonNull
    @Override
    public <K, V> DataSet<Pair<K, V>> handleQueryWithKey(Query<K> keyQuery, Query<V> valueQuery) throws InvalidQueryException {
        DataSet<Pair<K,V>> dataSet = DataSet.empty();

        for (final Table table : scrape()) {
            final DataSet<Pair<K, V>> tempDataSet = table.handleQueryWithKey(keyQuery, valueQuery);

            if (tempDataSet.getScore() > dataSet.getScore()) {
                dataSet = tempDataSet;
            }
        }

        return dataSet;
    }
}
