package com.reedoei.data.scraping.query;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */

public class Data<T> {
    private final DataType dataType;
    private final Set<T> dataValues;

    private final double score;

    private final String raw;

    public Data(DataType dataType, Set<T> dataValues, String raw, double score) {
        this.dataType = dataType;
        this.dataValues = dataValues;

        this.raw = raw;

        this.score = score;
    }

    public Set<T> getDataValues() {
        return dataValues;
    }
}
