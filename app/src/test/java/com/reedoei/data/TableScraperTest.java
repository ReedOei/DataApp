package com.reedoei.data;

import com.reedoei.data.scraping.query.Query;
import com.reedoei.data.scraping.query.QueryBuilder;
import com.reedoei.data.scraping.scraped.Table;
import com.reedoei.data.scraping.query.TableScraper;

import org.apache.commons.math3.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
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
            assertEquals(18, table.handleQuery(QueryBuilder.any().build()).getData().size());
        }
    }

    @Test
    public void testScrapeAges() throws Exception {
        final Document doc = Jsoup.parse(testSimpleTable);

        final TableScraper scraper = new TableScraper(doc);
        final Set<Table> result = scraper.scrape();

        assertEquals(1, result.size());

        for (final Table table : result) {
            final Set<Integer> dataSet = table.handleQuery(QueryBuilder.age().build()).asSet();

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
            final Set<Date> dataSet = table.handleQuery(QueryBuilder.date().build()).asSet();

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
            final Set<String> dataSet = table.handleQuery(QueryBuilder.general().keyword("food").build()).asSet();

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
            final Set<String> dataSet = table.handleQuery(QueryBuilder.phoneNumber().build()).asSet();

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
            final Set<Date> dataSet = table.handleQuery(QueryBuilder.date().keyword("birth").build()).asSet();

            assertEquals(3, dataSet.size());
        }
    }

    @Test
    public void testScrapeBirthdaysWithNamesAsKey() throws Exception {
        final Document doc = Jsoup.parse(testSimpleTable);

        final TableScraper scraper = new TableScraper(doc);
        final Set<Table> result = scraper.scrape();

        assertEquals(1, result.size());

        for (final Table table : result) {
            final Query<String> nameQuery = QueryBuilder.general().keyword("name").build();
            final Query<Date> valueQuery = QueryBuilder.date().keyword("birth").build();
            final Set<Pair<String, Date>> dataSet = table.handleQueryWithKey(nameQuery, valueQuery).asSet();

            assertEquals(3, dataSet.size());

            boolean found = false;

            for (final Pair<String, Date> pair : dataSet) {
                if (pair.getKey().equals("Mike")) {
                    LocalDate localDate = pair.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (localDate.getMonth().equals(Month.JUNE) &&
                            localDate.getDayOfMonth() == 6 &&
                            localDate.getYear() == 1988) {
                        found = true;
                    }
                }
            }

            assertTrue(found);
        }
    }

    @Test
    public void testScrapeWikipedia() throws Exception {
        final Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_presidents_of_the_United_States_by_age").get();

        final TableScraper scraper = new TableScraper(doc);
        final Set<Date> dataSet = scraper.handleQuery(QueryBuilder.date().keyword("birth").build()).asSet();

        boolean found = false;
        for (final Date date : dataSet) {
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Check that at least jimmy carter's birthday is on the list.
            if (localDate.getMonth().equals(Month.OCTOBER) &&
                    localDate.getDayOfMonth() == 1 &&
                    localDate.getYear() == 1924) {
                found = true;
            }
        }

        assertTrue(found);
    }
}
