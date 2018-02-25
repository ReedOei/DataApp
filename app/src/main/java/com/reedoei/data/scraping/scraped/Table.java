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
    // These are better than just finding stuff randomly in the row.
    private static final double INITIAL_CELL_MODIFIER = 2.0;
    private static final double HEADER_ROW_MODIFIER = 2.0;

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
            if (row.hasCells()) {
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
            final TableRow bestRow = findBestRow(query);

            if (bestRow == null) {
                return new HashSet<>();
            } else {
                return bestRow.query(query);
            }
        }
    }

    private <T> TableRow findBestRow(final Query<T> query) {
        TableRow bestRow = null;
        double maxScore = 0; // 0 is the min, all scores should be positive.

        for (final TableCell first : initialCells) {
            final double score = INITIAL_CELL_MODIFIER * query.getScore(first.getText());

            if (score > maxScore) {
                bestRow = first.getRow();
                maxScore = score;
            }
        }

        for (final TableCell header : headerRow) {
            final double score = HEADER_ROW_MODIFIER * query.getScore(header.getText());

            if (score > maxScore) {
                bestRow = header.getColumn();
                maxScore = score;
            }
        }

        for (final TableRow row : dataRows) {
            for (final TableCell cell : row.getCells()) {
                final double score = query.getScore(cell.getText());

                if (score > maxScore) {
                    bestRow = row;
                    maxScore = score;
                }
            }
        }

        return bestRow;
    }

    public TableRow getColumn(int index) {
        final List<TableCell> cells = new ArrayList<>();

        for (final TableRow row : dataRows) {
            if (row.hasColumn(index)) {
                cells.add(row.getCell(index));
            }
        }

        return new TableRow(this, cells);
    }
}
