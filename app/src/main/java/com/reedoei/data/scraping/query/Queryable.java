package com.reedoei.data.scraping.query;

import android.support.annotation.NonNull;
import android.util.Pair;

import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */

public interface Queryable {
    @NonNull
    default <T> Set<Data<T>> query(final Query<T> query) {
        return handleQuery(query).getData();
    }

    default <T> double getScore(Query<T> query) {
        return handleQuery(query).getScore();
    }

    @NonNull
    <T> DataSet<T> handleQuery(Query<T> query);
}
