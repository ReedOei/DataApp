package com.reedoei.data.scraping.query;

import android.support.annotation.NonNull;

import com.reedoei.data.data.DataSet;

import org.apache.commons.math3.util.Pair;
import org.jsoup.nodes.Document;

/**
 * Created by roei on 2/25/18.
 */

public class DocumentScraper implements Queryable {
    private final Document doc;
    private final TableScraper tableScraper;

    public DocumentScraper(final Document doc) {
        this.doc = doc;

        tableScraper = new TableScraper(doc);
    }

    @NonNull
    @Override
    public <T> DataSet<T> handleQuery(Query<T> query) throws InvalidQueryException {
        return tableScraper.handleQuery(query);
    }

    @NonNull
    @Override
    public <K, V> DataSet<Pair<K, V>> handleQueryWithKey(Query<K> keyQuery, Query<V> valueQuery) throws InvalidQueryException {
        return tableScraper.handleQueryWithKey(keyQuery, valueQuery);
    }
}
