package com.reedoei.data.scraping.query;

/**
 * Created by roei on 2/24/18.
 */

public class Data {
    private final DataType dataType;
    private final String dataTypeStr;
    private final String dataValue;

    private final String raw;

    public Data(DataType dataType, String dataTypeStr, String dataValue, String raw) {
        this.dataType = dataType;
        this.dataTypeStr = dataTypeStr;
        this.dataValue = dataValue;
        this.raw = raw;
    }
}
