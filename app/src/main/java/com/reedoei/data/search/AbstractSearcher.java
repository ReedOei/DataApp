package com.reedoei.data.search;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by roei on 2/25/18.
 */

public abstract class AbstractSearcher {
    public abstract List<Document> search(final Search searchQuery);
}
