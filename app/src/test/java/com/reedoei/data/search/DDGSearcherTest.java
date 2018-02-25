package com.reedoei.data.search;

import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by roei on 2/25/18.
 */
public class DDGSearcherTest {
    @Test
    public void search() throws Exception {
        final List<Document> documents = new DDGSearcher().search(new Search("apple"));

        assertFalse(documents.isEmpty());
    }
}