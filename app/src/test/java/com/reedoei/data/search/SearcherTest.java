package com.reedoei.data.search;

import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by roei on 2/25/18.
 */
public class SearcherTest {
    @Test
    public void search() throws Exception {
        final List<Document> documents = new Searcher(new Search("apple")).search();

        assertFalse(documents.isEmpty());
    }
}