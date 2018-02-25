package com.reedoei.data.scraping.scraped;

import org.jsoup.nodes.Element;

/**
 * Created by roei on 2/24/18.
 */

public abstract class AbstractScraped {
    private final Element element;

    public AbstractScraped(final Element element) {
        this.element = element;
    }
}
