package com.reedoei.data.scraping.query;

import android.support.annotation.NonNull;

import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */

public interface Queryable {
    @NonNull
    <T> Set<Data<T>> query(final Query<T> query);
}
