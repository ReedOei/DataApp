package com.reedoei.data;

import com.reedoei.data.scraping.query.Data;
import com.reedoei.data.scraping.query.Query;
import com.reedoei.data.scraping.scraped.Table;
import com.reedoei.data.scraping.query.TableScraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

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
                    "       <th>Phone Number</th>" +
                    "       <th>Favorite Food</th>" +
                    "       <th>Birthday</th>" +
                    "   </tr>" +
                    "   <tr>" +
                    "       <td>John</td>" +
                    "       <td>February 21, 2018</td>" +
                    "       <td>36</td>" +
                    "       <td>123-456-7890</td>" +
                    "       <td>Apples</td>" +
                    "       <td>1998-11-26</td>" +
                    "   </tr>" +
                    "   <tr>" +
                    "       <td>Mary</td>" +
                    "       <td>August 7, 345</td>" +
                    "       <td>6</td>" +
                    "       <td>183-456-7890</td>" +
                    "       <td>Pie</td>" +
                    "       <td>1980-05-23</td>" +
                    "   </tr>" +
                    "   <tr>" +
                    "       <td>Mike</td>" +
                    "       <td>1457-9-08</td>" +
                    "       <td>100</td>" +
                    "       <td>269-456-7890</td>" +
                    "       <td>Steak</td>" +
                    "       <td>June 6, 1988</td>" +
                    "   </tr>" +
                    "</table>";

    @Test
    public void testScrapeString() throws Exception {
        final Document doc = Jsoup.parse(testSimpleTable);

        final TableScraper scraper = new TableScraper(doc);
        final Set<Table> result = scraper.scrape();

        assertEquals(1, result.size());

        for (final Table table : result) {
            assertEquals(15, table.query(Query.any()).size());
        }
    }

    @Test
    public void testScrapeAges() throws Exception {
        final Document doc = Jsoup.parse(testSimpleTable);

        final TableScraper scraper = new TableScraper(doc);
        final Set<Table> result = scraper.scrape();

        assertEquals(1, result.size());

        for (final Table table : result) {
            final Set<Integer> dataSet = Data.collapseDataset(table.query(Query.age()));

            assertEquals(3, dataSet.size());
            assertThat(dataSet, hasItems(100, 36, 6));
        }
    }

    @Test
    public void testScrapeDates() throws Exception {
        final Document doc = Jsoup.parse(testSimpleTable);

        final TableScraper scraper = new TableScraper(doc);
        final Set<Table> result = scraper.scrape();

        assertEquals(1, result.size());

        for (final Table table : result) {
            final Set<Date> dataSet = Data.collapseDataset(table.query(Query.date()));

            assertEquals(3, dataSet.size());
        }
    }

    @Test
    public void testGeneralScraper() throws Exception {
        final Document doc = Jsoup.parse(testSimpleTable);

        final TableScraper scraper = new TableScraper(doc);
        final Set<Table> result = scraper.scrape();

        assertEquals(1, result.size());

        for (final Table table : result) {
            final Set<String> dataSet = Data.collapseDataset(table.query(Query.general("food")));

            assertEquals(3, dataSet.size());
            assertThat(dataSet, hasItems("Steak", "Apples", "Pie"));
        }
    }

    @Test
    public void testPhoneNumberScraper() throws Exception {
        final Document doc = Jsoup.parse(testSimpleTable);

        final TableScraper scraper = new TableScraper(doc);
        final Set<Table> result = scraper.scrape();

        assertEquals(1, result.size());

        for (final Table table : result) {
            final Set<String> dataSet = Data.collapseDataset(table.query(Query.phoneNumber()));

            assertEquals(3, dataSet.size());
            assertThat(dataSet, hasItems("269-456-7890", "123-456-7890", "183-456-7890"));
        }
    }

    @Test
    public void testScrapeBirthdays() throws Exception {
        final Document doc = Jsoup.parse(testSimpleTable);

        final TableScraper scraper = new TableScraper(doc);
        final Set<Table> result = scraper.scrape();

        assertEquals(1, result.size());

        for (final Table table : result) {
            final Set<Date> dataSet = Data.collapseDataset(table.query(Query.date(Collections.singletonList("birth"))));

            assertEquals(3, dataSet.size());
        }
    }

    @Test
    public void scrapeWikipedia() throws Exception {
        final Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_presidents_of_the_United_States_by_age").get();

        final TableScraper scraper = new TableScraper(doc);
        final Set<Date> dataSet = Data.collapseDataset(scraper.query(Query.date()));
    }
}