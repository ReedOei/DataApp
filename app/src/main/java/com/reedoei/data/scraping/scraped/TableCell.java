package com.reedoei.data.scraping.scraped;

import android.support.annotation.NonNull;

import com.reedoei.data.scraping.query.Data;
import com.reedoei.data.scraping.query.DataSet;
import com.reedoei.data.scraping.query.Query;
import com.reedoei.data.scraping.query.Queryable;

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

    @NonNull
    @Override
    public <T> Set<Data<T>> query(final Query<T> query) {
        return Collections.singleton(query.scrape(text));
    }

    @Override
    public <T> double getScore(Query<T> query) {
        return row.getTable().getScore(query);
    }

    @NonNull
    @Override
    public <T> DataSet<T> handleQuery(Query<T> query) {
        final double score = row.getTable().getScore(query);

        return new DataSet<>(score, Collections.singleton(query.scrape(text)));
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
