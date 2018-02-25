package com.reedoei.data.scraping.scraped;

import android.support.annotation.NonNull;

import com.reedoei.data.scraping.query.Data;
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

    public TableCell(final Element element) {
        super(element);

        text = element.text();
    }

    @NonNull
    @Override
    public <T> Set<Data<T>> query(final Query<T> query) {
        return Collections.singleton(query.scrape(text));
    }
}
