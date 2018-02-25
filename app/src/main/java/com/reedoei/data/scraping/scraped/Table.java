package com.reedoei.data.scraping.scraped;

import android.support.annotation.NonNull;

import com.reedoei.data.scraping.query.Data;
import com.reedoei.data.scraping.query.DataType;
import com.reedoei.data.scraping.query.Query;
import com.reedoei.data.scraping.query.Queryable;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */

public class Table extends AbstractScraped implements Queryable {
    private final List<TableCell> headerRow = new ArrayList<>();
    private final List<TableCell> initialCells = new ArrayList<>();
    private final List<TableRow> dataRows = new ArrayList<>();

    public Table(final Element element) {
        super(element);

        for (final Element tr : element.getElementsByTag("tr")) {
            if (headerRow.isEmpty()) {
                headerRow.addAll(new TableRow(this, tr).getCells());
            } else {
                dataRows.add(new TableRow(this, tr));
            }
        }

        for (final TableRow row : dataRows) {
            if (!row.hasCells()) {
                initialCells.add(row.getCells().get(0));
            }
        }
    }

    @NonNull
    @Override
    public <T> Set<Data<T>> query(final Query<T> query) {
        if (query.getDataType().equals(DataType.ANY)) {
            final Set<Data<T>> result = new HashSet<>();

            for (final TableRow row : dataRows) {
                result.addAll(row.query(query));
            }

            return result;
        } else {
            return new HashSet<>();
        }
    }
}
