package com.reedoei.data.scraping.query;

import android.support.annotation.NonNull;

import com.reedoei.data.data.DataSet;
import com.reedoei.data.scraping.scraped.Table;

import org.apache.commons.math3.util.Pair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roei on 2/24/18.
 */

public class TableScraper implements Queryable {
    private final Document doc;

    private final List<Table> tables = new ArrayList<>();

    public TableScraper(final Document doc) {
        this.doc = doc;
    }

    public final List<Table> scrape() {
        if (!tables.isEmpty()) {
            return tables;
        }

        for (final Element element : doc.getElementsByTag("table")) {
            tables.add(new Table(element));
        }

        return tables;
    }

    @NonNull
    @Override
    public <T> DataSet<T> handleQuery(Query<T> query) {
        DataSet<T> dataSet = DataSet.empty();

        System.out.println("Querying " + scrape().size() + " tables with " +
                scrape().stream().map(t -> t.getRows().size()).mapToInt(Integer::intValue).sum() + " rows.");

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
