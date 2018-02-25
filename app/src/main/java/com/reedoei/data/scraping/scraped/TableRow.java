package com.reedoei.data.scraping.scraped;

import android.support.annotation.NonNull;

import com.reedoei.data.scraping.query.Data;
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
    public <T> Set<Data<T>> query(final Query<T> query) {
        final Set<Data<T>> result = new HashSet<>();

        for (final TableCell cell : cells) {
            result.addAll(cell.query(query));
        }

        return result;
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
}
