package com.reedoei.data.scraping.query;

import org.apache.commons.math3.util.Pair;

import java.util.List;

/**
 * Created by roei on 2/24/18.
 */

public class Data<T> {
    private final DataType dataType;
    private final List<T> dataValues;

    private final double score;

    private final String raw;

    public Data(DataType dataType, List<T> dataValues, String raw, double score) {
        this.dataType = dataType;
        this.dataValues = dataValues;

        this.raw = raw;

        this.score = score;
    }

    public List<T> getDataValues() {
        return dataValues;
    }

    public String toCSV() {
        String result = "";

        for (final T value : dataValues) {
            result += getCSV(value);
        }

        return result;
    }

    private String getCSV(T value) {
        if (value instanceof Pair) {
            return ((Pair) value).getKey() + "," + ((Pair) value).getValue() + System.lineSeparator();
        } else {
            return value.toString() + System.lineSeparator();
        }
    }
}
