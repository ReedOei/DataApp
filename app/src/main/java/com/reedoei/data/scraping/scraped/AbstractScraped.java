package com.reedoei.data.scraping.scraped;

import android.support.annotation.Nullable;

import org.jsoup.nodes.Element;

/**
 * Created by roei on 2/24/18.
 */

public abstract class AbstractScraped {
    @Nullable
    private final Element element;

    public AbstractScraped(final Element element) {
        this.element = element;
    }
}
