package com.reedoei.data.scraping.scraped;

import android.support.annotation.NonNull;

import com.reedoei.data.scraping.query.DataSet;
import com.reedoei.data.scraping.query.DataType;
import com.reedoei.data.scraping.query.InvalidQueryException;
import com.reedoei.data.scraping.query.Query;
import com.reedoei.data.scraping.query.Queryable;

import org.apache.commons.math3.util.Pair;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

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
    public <T> DataSet<T> handleQuery(Query<T> query) {
        if (query.getDataType().equals(DataType.ANY)) {
            final DataSet<T> result = DataSet.empty();

            for (final TableRow row : dataRows) {
                result.combine(row.handleQuery(query));
            }

            return result;
        } else {
            final Pair<Double, TableRow> bestRow = findBestRow(query);

            if (bestRow.getValue() == null) {
                return DataSet.empty();
            } else {
                return bestRow.getValue().handleQuery(query);
            }
        }
    }

    @NonNull
    @Override
    public <K, V> DataSet<Pair<K, V>> handleQueryWithKey(Query<K> keyQuery, Query<V> valueQuery) throws InvalidQueryException {
        if (valueQuery.getDataType().equals(DataType.ANY)) {
            throw new InvalidQueryException("Cannot do a key query on a table with DataType.ANY!");
        } else {
            DataSet<K> keys = DataSet.empty();
            DataSet<V> values = DataSet.empty();

            final Pair<Double, TableRow> bestKeyRow = findBestRow(keyQuery);

            if (bestKeyRow.getValue() == null) {
                return DataSet.empty();
            } else {
                keys = bestKeyRow.getValue().handleQuery(keyQuery);
            }

            final Pair<Double, TableRow> bestValueRow = findBestRow(valueQuery);

            if (bestValueRow.getValue() == null) {
                return DataSet.empty();
            } else {
                values = bestValueRow.getValue().handleQuery(valueQuery);
            }

            return DataSet.zip(keys, values);
        }
    }

    // Override because this gets called in TableRow and TableCell, so would be infinite loop.
    @Override
    public <T> double getScore(Query<T> query) {
        return findBestRow(query).getKey();
    }

    private <T> Pair<Double, TableRow> findBestRow(final Query<T> query) {
        TableRow bestRow = null;
        double maxScore = 0; // 0 is the min, all scores should be positive.

        for (final TableCell first : initialCells) {
            final double score = INITIAL_CELL_MODIFIER * query.getScore(first.getText(), first.getRow().asStrings());

            if (score > maxScore) {
                bestRow = first.getRow();
                maxScore = score;
            }
        }

        for (final TableCell header : headerRow) {
            final double score = HEADER_ROW_MODIFIER * query.getScore(header.getText(), header.getColumn().asStrings());

            if (score > maxScore) {
                bestRow = header.getColumn();
                maxScore = score;
            }
        }

        for (final TableRow row : dataRows) {
            for (final TableCell cell : row.getCells()) {
                final double score = query.getScore(cell.getText(), row.asStrings());

                if (score > maxScore) {
                    bestRow = row;
                    maxScore = score;
                }
            }
        }

        return new Pair<>(maxScore, bestRow);
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

    public List<TableRow> getRows() {
        return dataRows;
    }
}
