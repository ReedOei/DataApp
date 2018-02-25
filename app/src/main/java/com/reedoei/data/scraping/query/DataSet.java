package com.reedoei.data.scraping.query;

import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */

public class DataSet<T> implements Iterable<Data<T>>  {
    public static <T> DataSet<T> empty() {
        return new DataSet<>(0, new HashSet<>());
    }

    private double score;
    private final Set<Data<T>> data;

    public DataSet(double score, Set<Data<T>> data) {
        this.score = score;
        this.data = data;
    }

    public double getScore() {
        return score;
    }

    public Set<Data<T>> getData() {
        return data;
    }

    public DataSet<T> combine(final DataSet<T> data) {
        for (final Data<T> d : data) {
            this.data.add(d);
        }

        return this;
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
}
