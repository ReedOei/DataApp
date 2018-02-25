package com.reedoei.data.search;

import com.reedoei.data.scraping.scraped.AbstractScraped;

import org.jsoup.nodes.Element;

/**
 * Created by roei on 2/25/18.
 */

public class DDGSearchResult extends AbstractScraped {
    private String resultURL;

    public DDGSearchResult(Element element) {
        super(element);

        for (final Element e : element.getElementsByClass("result__a")) {
            if (e.hasAttr("href")) {
                resultURL = e.attr("href");
            }
        }
    }

    public String getResultURL() {
        return resultURL;
    }
}
