package com.reedoei.data.search;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roei on 2/25/18.
 */

public class DDGSearchScraper {
    private final Document doc;

    public DDGSearchScraper(final Document doc) {
        this.doc = doc;
    }

    public List<DDGSearchResult> scrape() {
        final List<DDGSearchResult> result = new ArrayList<>();

        for (final Element element : doc.getElementsByClass("result")) {
            result.add(new DDGSearchResult(element));
        }

        return result;
    }
}
