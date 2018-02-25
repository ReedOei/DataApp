package com.reedoei.data.scraping.scraped;

import android.support.annotation.NonNull;

import com.reedoei.data.scraping.query.Data;
import com.reedoei.data.scraping.query.DataSet;
import com.reedoei.data.scraping.query.InvalidQueryException;
import com.reedoei.data.scraping.query.Query;
import com.reedoei.data.scraping.query.Queryable;

import org.apache.commons.math3.util.Pair;
import org.jsoup.nodes.Element;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */
class TableCell extends AbstractScraped implements Queryable {
    private final String text;
    private final TableRow row;

    public TableCell(final TableRow row, final Element element) {
        super(element);

        text = element.text();
        this.row = row;
    }

    @Override
    public <T> double getScore(Query<T> query) {
        return row.getTable().getScore(query);
    }

    @NonNull
    @Override
    public <T> DataSet<T> handleQuery(Query<T> query) {
        final double score = row.getTable().getScore(query);

        return new DataSet<>(score, Collections.singletonList(query.scrape(text)));
    }

    @NonNull
    @Override
    public <K, V> DataSet<Pair<K, V>> handleQueryWithKey(Query<K> keyQuery, Query<V> valueQuery) throws InvalidQueryException {
        throw new InvalidQueryException("Cannot handle query with key on table cell!");
    }

    public String getText() {
        return text;
    }

    public TableRow getRow() {
        return row;
    }

    public TableRow getColumn() {
        return row.getColumn(this);
    }
}
