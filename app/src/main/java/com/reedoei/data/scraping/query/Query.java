package com.reedoei.data.scraping.query;

/**
 * Created by roei on 2/24/18.
 */

public class Query {
    private final DataType dataType;
    private final String dataTypeStr;

    public Query(DataType dataType, String dataTypeStr) {
        this.dataType = dataType;
        this.dataTypeStr = dataTypeStr;
    }
}
