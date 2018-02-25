package com.reedoei.data.search;

import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by roei on 2/25/18.
 */

public class Search {
    private final List<String> keywords;

    public Search(List<String> keywords) {
        this.keywords = keywords;
    }

    public Search(String... keywords) {
        this(new ArrayList<>(Arrays.asList(keywords)));
    }

    private String constructGoogleSearchURL() {
        try {
            return new URIBuilder("https://duckduckgo.com/html/")
                    .addParameter("q", String.join(" ", keywords))
                    .build()
                    .toString();
        } catch (URISyntaxException ignored) {}

        return "";
    }

    public List<String> findURLs() {
        try {
            final Document doc = Jsoup.connect(constructGoogleSearchURL()).get();

            return new DDGSearchScraper(doc).scrape().stream()
                    .map(DDGSearchResult::getResultURL)
                    .collect(Collectors.toList());
        } catch (IOException ignored) {}

        return new ArrayList<>();
    }
}
