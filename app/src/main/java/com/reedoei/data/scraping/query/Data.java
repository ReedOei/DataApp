package com.reedoei.data.scraping.query;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */

public class Data<T> {
    private final DataType dataType;
    private final String dataTypeStr;
    private final Set<T> dataValues;

    private final double score;

    private final String raw;

    public Data(DataType dataType, String dataTypeStr, Set<T> dataValues, String raw, double score) {
        this.dataType = dataType;
        this.dataTypeStr = dataTypeStr;
        this.dataValues = dataValues;

        this.raw = raw;

        this.score = score;
    }

    public static <T> Set<T> collapseDataset(final Set<Data<T>> dataSet) {
        final Set<T> result = new HashSet<>();

        for (final Data<T> data : dataSet) {
            result.addAll(data.dataValues);
        }

        return result;
    }
}
