package com.reedoei.data.search;

import android.net.Uri;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by roei on 2/25/18.
 */
public class SearchTest {
    @Test
    public void findUris() throws Exception {
        final List<String> urls = new Search("test").findURLs();

        assertEquals(30, urls.size());
    }
}