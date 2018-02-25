package com.reedoei.data.data;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.apache.commons.math3.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by roei on 2/24/18.
 */
public class DataSet<T> implements Iterable<Data<T>>  {
    public static <T> DataSet<T> empty() {
        return new DataSet<>(0, new ArrayList<>());
    }

    public static <T> DataSet<T> load(final String json,
                                      final Function<String, T> parser) {
        Gson gson = new Gson();
        return loadWrapper(gson.fromJson(json, DataSetWrapper.class), parser);
    }

    public static <K, V> DataSet<Pair<K,V>> load(final String json,
                                                 final Function<String, K> keyParser,
                                                 final Function<String, V> valueParser) {
        Gson gson = new Gson();

        Function<String, Pair<K, V>> parser = str -> {
            final String[] split = str.split(",");
            return new Pair<K, V>(keyParser.apply(split[0]), valueParser.apply(split[1]));
        };

        return loadWrapper(gson.fromJson(json, DataSetWrapper.class), parser);
    }

    static <T> DataSet<T> loadWrapper(final DataSetWrapper wrapper,
                                      final Function<String, T> parser) {
        final List<Data<T>> values = new ArrayList<>();

        for (DataWrapper data : wrapper.dataValues) {
            values.add(Data.loadWrapper(data, parser));
        }

        return new DataSet<>(wrapper.score, values);
    }

    private double score;
    private final List<Data<T>> data;

    public DataSet(double score, List<Data<T>> data) {
        this.score = score;
        this.data = data;
    }

    public double getScore() {
        return score;
    }

    public List<Data<T>> getData() {
        return data;
    }

    public DataSet<T> combine(final DataSet<T> data) {
        for (final Data<T> d : data) {
            this.data.add(d);
        }

        return this;
    }

    public JSONObject getJSON() {
        JSONObject object = new JSONObject();

        object.put("score", Double.toString(score));

        JSONArray dataValues = new JSONArray();

        for (final Data<T> d : data) {
            dataValues.add(d.getJSON());
        }

        object.put("dataValues", dataValues);

        return object;
    }

    public String toJSON() {
        return getJSON().toJSONString();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    @NonNull
    @Override
    public Iterator<Data<T>> iterator() {
        return data.iterator();
    }

    public DataSet<T> withScore(double score) {
        this.score = score;
        return this;
    }

    public Set<T> asSet() {
        final Set<T> result = new HashSet<>();

        for (final Data<T> d : data) {
            result.addAll(d.getDataValues());
        }

        return result;
    }

    public static <K, V> DataSet<Pair<K,V>> zip(DataSet<K> keys, DataSet<V> values) {
        final List<Data<Pair<K,V>>> data = new ArrayList<>();

        double score = Math.max(keys.getScore(), values.getScore());

        for (int k = 0; k < keys.data.size(); k++) {
            if (k >= values.data.size()) {
                break;
            }

            final List<Pair<K,V>> pairs = new ArrayList<>();

            final List<K> keyVals = keys.data.get(k).getDataValues();
            final List<V> vals = values.data.get(k).getDataValues();

            for (int j = 0; j < keyVals.size(); j++) {
                if (j >= vals.size()) {
                    break;
                }

                pairs.add(new Pair<>(keyVals.get(j), vals.get(j)));
            }

            data.add(new Data<>(DataType.KEY_VALUE, pairs, "", score));
        }

        return new DataSet<>(score, data);
    }
}
