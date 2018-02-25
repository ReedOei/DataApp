package com.reedoei.data.scraping;

import com.reedoei.data.scraping.query.Data;
import com.reedoei.data.scraping.query.Query;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roei on 2/24/18.
 */

public class Table extends Scraped {
    private class TableCell extends Scraped {
        private final String text;

        public TableCell(final Element element) {
            super(element);

            text = element.text();
        }
    }

    private class TableRow extends Scraped {
        private final List<TableCell> cells = new ArrayList<>();

        public TableRow(final Element element) {
            super(element);

            for (final Element td : element.getElementsByTag("td")) {
                cells.add(new TableCell(td));
            }
        }
    }

    private final List<TableCell> headers = new ArrayList<>();
    private final List<TableCell> initialCells = new ArrayList<>();
    private final List<TableRow> rows = new ArrayList<>();

    public Table(final Element element) {
        super(element);

        for (final Element tr : element.getElementsByTag("tr")) {
            rows.add(new TableRow(tr));
        }

        headers.addAll(new ArrayList<>(rows.get(0).cells));

        for (final TableRow row : rows) {
            if (!row.cells.isEmpty()) {
                initialCells.add(row.cells.get(0));
            }
        }
    }

    public List<Data> findData(final Query query) {
        return new ArrayList<>();
    }
}
