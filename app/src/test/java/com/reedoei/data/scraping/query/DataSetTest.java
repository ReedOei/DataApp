package com.reedoei.data.scraping.query;

import com.reedoei.data.TestingConstants;

import org.jsoup.Jsoup;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by roei on 2/25/18.
 */
public class DataSetTest {
    @Test
    public void toCSV() throws Exception {
        final DocumentScraper scraper = new DocumentScraper(Jsoup.parse(TestingConstants.testSimpleTable));

        assertEquals(
                "123-456-7890\n" +
                "183-456-7890\n" +
                "269-456-7890\n",
                scraper.handleQuery(QueryBuilder.phoneNumber().build()).toCSV());
    }
}