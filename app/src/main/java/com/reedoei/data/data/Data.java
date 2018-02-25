package com.reedoei.data.data;

import org.apache.commons.math3.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by roei on 2/24/18.
 */

public class Data<T> {
    static <T> Data<T> loadWrapper(final DataWrapper data, final Function<String, T> parser) {
        final List<T> dataValues = new ArrayList<>();

        for (final String valueStr : data.dataValues) {
            dataValues.add(parser.apply(valueStr));
        }

        return new Data<>(data.dataType, dataValues, data.raw, data.score);
    }

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

    private String getString(T value) {
        if (value instanceof Pair) {
            return ((Pair) value).getKey() + "," + ((Pair) value).getValue();
        } else {
            return value.toString();
        }
    }

    public JSONObject getJSON() {
        JSONObject object = new JSONObject();

        object.put("raw", raw);
        object.put("score", Double.toString(score));
        object.put("dataType", dataType);

        JSONArray dataValuesJSON = new JSONArray();
        for (final T value : dataValues) {
            dataValuesJSON.add(getString(value));
        }

        object.put("dataValues", dataValuesJSON);

        return object;
    }
}
