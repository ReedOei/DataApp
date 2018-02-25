package com.reedoei.data;

import com.reedoei.data.scraping.query.Query;
import com.reedoei.data.scraping.scraped.Table;
import com.reedoei.data.scraping.query.TableScraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by roei on 2/24/18.
 */
public class TableScraperTest {
    private final static String testSimpleTable =
                    "<table>" +
                    "   <tr>" +
                    "       <th>Name</th>" +
                    "       <th>Date</th>" +
                    "       <th>Age</th>" +
                "           <th>Phone Number</th>" +
                    "   </tr>" +
                    "   <tr>" +
                    "       <td>John</td>" +
                    "       <td>February 21, 2018</td>" +
                    "       <td>36</td>" +
                    "       <td>123-456-7890</td>" +
                    "   </tr>" +
                    "   <tr>" +
                    "       <td>Mary</td>" +
                    "       <td>August 7, 345</td>" +
                    "       <td>6</td>" +
                    "       <td>183-456-7890</td>" +
                    "   </tr>" +
                    "   <tr>" +
                    "       <td>Mike</td>" +
                    "       <td>1457-9-08</td>" +
                    "       <td>100</td>" +
                    "       <td>269-456-7890</td>" +
                    "   </tr>" +
                    "</table>";

    @Test
    public void testScrapeString() throws Exception {
        final Document doc = Jsoup.parse(testSimpleTable);

        final TableScraper scraper = new TableScraper(doc);
        final Set<Table> result = scraper.scrape();

        assertEquals(1, result.size());

        for (final Table table : result) {
            assertEquals(12, table.query(Query.getAll()).size());
        }
    }

    @Test
    public void scrapeWikipedia() throws Exception {
        final Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/George_Washington").get();

        final TableScraper scraper = new TableScraper(doc);
        final Set<Table> result = scraper.scrape();
    }

    @Test
    public void scrapeWheatInformation() throws Exception {
        final Document doc = Jsoup.connect("https://www.google.com/search?q=wheat").get();

        final TableScraper scraper = new TableScraper(doc);
        final Set<Table> result = scraper.scrape();
    }
}