package com.reedoei.data.scraping.scraped;

import android.support.annotation.NonNull;

import com.reedoei.data.data.DataSet;
import com.reedoei.data.scraping.query.InvalidQueryException;
import com.reedoei.data.scraping.query.Query;
import com.reedoei.data.scraping.query.Queryable;

import org.apache.commons.math3.util.Pair;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roei on 2/24/18.
 */
class TableRow extends AbstractScraped implements Queryable {
    private Table table;
    private final List<TableCell> cells = new ArrayList<>();

    public TableRow(final Table table, final List<TableCell> cells) {
        super(null);

        this.table = table;
        this.cells.addAll(cells);
    }

    public TableRow(Table table, final Element element) {
        super(element);
        this.table = table;

        for (final Element td : element.getAllElements()) {
            if (td.tagName().equals("td") || td.tagName().equals("th")) {
                cells.add(new TableCell(this, td));
            }
        }
    }

    public List<TableCell> getCells() {
        return cells;
    }

    public boolean hasCells() {
        return !cells.isEmpty();
    }

    @NonNull
    @Override
    public <T> DataSet<T> handleQuery(Query<T> query) {
        double score = table.getScore(query);

        final DataSet<T> result = DataSet.empty();

        for (final TableCell cell : cells) {
            result.combine(cell.handleQuery(query));
        }

        return result.withScore(score);
    }

    @NonNull
    @Override
    public <K, V> DataSet<Pair<K, V>> handleQueryWithKey(Query<K> keyQuery, Query<V> valueQuery) throws InvalidQueryException {
        throw new InvalidQueryException("Cannot handle query with key on table row!");
    }

    public TableRow getColumn(final TableCell tableCell) {
        final int index = cells.indexOf(tableCell);

        return table.getColumn(index);
    }

    public TableCell getCell(int index) {
        return cells.get(index);
    }

    public boolean hasColumn(int index) {
        return cells.size() > index;
    }

    public Table getTable() {
        return table;
    }

    public List<String> asStrings() {
        final List<String> result = new ArrayList<>();

        for (final TableCell cell : cells) {
            result.add(cell.getText());
        }

        return result;
    }
}
