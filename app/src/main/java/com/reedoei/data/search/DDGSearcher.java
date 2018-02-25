package com.reedoei.data.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roei on 2/25/18.
 */
public class DDGSearcher extends AbstractSearcher {
    @Override
    public List<Document> search(Search searchQuery) {
        final List<Document> result = new ArrayList<>();

        for (final String url: searchQuery.findURLs()) {
            try {
                result.add(Jsoup.connect(url).get());
            } catch (IOException ignored) {}
        }

        return result;
    }
}
