package com.reedoei.data.scraping.query;

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
}
