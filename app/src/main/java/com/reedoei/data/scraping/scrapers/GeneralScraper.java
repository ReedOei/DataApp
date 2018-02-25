package com.reedoei.data.scraping.scrapers;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */
public class GeneralScraper extends AbstractScraper<String> {
    public GeneralScraper(final List<String> keywords) {
        super(keywords);
    }

    @Override
    public double getScore(final String text) {
        return getKeywordScore(text);
    }

    @NonNull
    @Override
    public List<String> scrapeData(final String text) {
        return Collections.singletonList(text);
    }
}
