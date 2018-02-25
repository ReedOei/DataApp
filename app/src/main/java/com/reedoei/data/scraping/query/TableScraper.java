package com.reedoei.data.scraping.query;

import android.support.annotation.NonNull;

import com.reedoei.data.scraping.scraped.Table;

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
    public Set<Data> query(final Query query) {
        return new HashSet<>();
    }
}
