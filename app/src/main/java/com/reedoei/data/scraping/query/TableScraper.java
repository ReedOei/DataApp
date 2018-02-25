package com.reedoei.data.scraping.query;

import android.support.annotation.NonNull;
import android.util.Pair;

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
    public <T> DataSet<T> handleQuery(Query<T> query) {
        Set<Data<T>> dataSet = new HashSet<>();

        double maxScore = 0;

        // Return the data set with the highest score;
        for (final Table table : scrape()) {
            final Set<Data<T>> tempDataSet = table.query(query);
            double score = table.getScore(query);

            if (score > maxScore) {
                maxScore = score;
                dataSet = tempDataSet;
            }
        }

        return new DataSet<>(maxScore, dataSet);
    }
}
