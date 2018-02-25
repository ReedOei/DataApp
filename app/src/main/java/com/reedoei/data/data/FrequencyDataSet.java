package com.reedoei.data.data;

import android.support.annotation.NonNull;

import org.apache.commons.math3.stat.Frequency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by roei on 2/25/18.
 */
public class FrequencyDataSet<T extends Comparable<?>> extends NumericDataSet {
    private final Map<T, Integer> frequencyMap = new HashMap<>();
    private int total = 0;

    private final Frequency frequency = new Frequency();

    private static <K> BiFunction<K, Integer, Integer> incrementBy(int amount) {
        return (k, count) -> {
            if (count == null) {
                return amount;
            } else {
                return count + amount;
            }
        };
    }

    private FrequencyDataSet constructFrom(final Map<T, Integer> frequencyMap) {
        this.frequencyMap.putAll(frequencyMap);

        for (final T key : frequencyMap.keySet()) {
            for (int i = 0; i < frequencyMap.get(key); i++) {
                frequency.addValue(key);
            }
        }

        return this;
    }

    public FrequencyDataSet(final Map<T, Integer> frequencyMap) {
        constructFrom(frequencyMap);
    }

    public FrequencyDataSet(final DataSet<T> data) {
        final Map<T, Integer> newFreqMap = new HashMap<>();

        for (final Data<T> d : data) {
            for (final T val : d) {
                newFreqMap.compute(val, incrementBy(1));
                total++;
            }
        }

        constructFrom(newFreqMap);
    }

    @NonNull
    public Optional<FrequencyDataSet<T>> expectedDistribution(final DistributionType distribution) {
        switch (distribution) {
            case UNIFORM:
                final Map<T, Integer> uniformDistributionMap = new HashMap<>();

                for (final T key : frequencyMap.keySet()) {
                    uniformDistributionMap.put(key, total / frequencyMap.keySet().size());
                }

                return Optional.of(new FrequencyDataSet<T>(uniformDistributionMap));

            default:
                return Optional.empty();
        }
    }

    @NonNull
    @Override
    public List<Double> values() {
        return frequencyMap.values().stream()
                .map(Integer::doubleValue)
                .collect(Collectors.toList());
    }
}
