package com.reedoei.data.data;

import android.support.annotation.NonNull;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.List;

/**
 * Created by roei on 2/25/18.
 */
public abstract class NumericDataSet {
    @NonNull
    public DescriptiveStatistics summary() {
        final DescriptiveStatistics result = new DescriptiveStatistics();

        values().forEach(result::addValue);

        return result;
    }

    @NonNull
    public abstract List<Double> values();
}
