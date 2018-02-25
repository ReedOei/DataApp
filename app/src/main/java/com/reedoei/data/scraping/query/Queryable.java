package com.reedoei.data.scraping.query;

import android.support.annotation.NonNull;

import org.apache.commons.math3.util.Pair;

/**
 * Created by roei on 2/24/18.
 */
public interface Queryable {
    default <T> double getScore(Query<T> query) throws InvalidQueryException {
        return handleQuery(query).getScore();
    }

    @NonNull
    <T> DataSet<T> handleQuery(Query<T> query) throws InvalidQueryException;

    @NonNull
    <K, V> DataSet<Pair<K, V>> handleQueryWithKey(Query<K> keyQuery, Query<V> valueQuery) throws InvalidQueryException;
}
