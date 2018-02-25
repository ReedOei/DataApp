package com.reedoei.data.scraping.scrapers;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

/**
 * Created by roei on 2/24/18.
 */
public class GeneralScraper extends AbstractScraper<String> {
    public GeneralScraper(final List<String> keywords) {
        super(keywords);
    }

    @Override
    public double getDataFactor(List<String> potentialData) {
        return 1.0;
    }

    @Override
    public double getKeywordFactor(final String text) {
        return 1.0;
    }

    @NonNull
    @Override
    public List<String> scrapeData(final String text) {
        return Collections.singletonList(text);
    }
}
