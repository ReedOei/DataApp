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
    public void toJSON() throws Exception {
        final DocumentScraper scraper = new DocumentScraper(Jsoup.parse(TestingConstants.testSimpleTable));

        System.out.println(scraper.handleQuery(QueryBuilder.phoneNumber().build()).toJSON());

        assertEquals(
                "{\"score\":\"9.4\",\"dataValues\":[{\"score\":\"1.0\",\"dataValues\":[\"123-456-7890\"],\"dataType\":PHONE_NUMBER,\"raw\":\"123-456-7890\"},{\"score\":\"1.0\",\"dataValues\":[\"183-456-7890\"],\"dataType\":PHONE_NUMBER,\"raw\":\"183-456-7890\"},{\"score\":\"1.0\",\"dataValues\":[\"269-456-7890\"],\"dataType\":PHONE_NUMBER,\"raw\":\"269-456-7890\"}]}",
                scraper.handleQuery(QueryBuilder.phoneNumber().build()).toJSON());
    }
}