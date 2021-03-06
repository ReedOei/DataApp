package com.reedoei.data.scraping.query;

import com.reedoei.data.scraping.scraped.Table;

import org.apache.commons.math3.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.reedoei.data.TestingConstants.testSimpleTable;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by roei on 2/24/18.
 */
public class TableScraperTest {
    @Test
    public void testScrapeString() throws Exception {
        final Document doc = Jsoup.parse(testSimpleTable);

        final TableScraper scraper = new TableScraper(doc);
        final List<Table> result = scraper.scrape();

        assertEquals(1, result.size());

        for (final Table table : result) {
            assertEquals(18, table.handleQuery(QueryBuilder.any().build()).getData().size());
        }
    }

    @Test
    public void testScrapeAges() throws Exception {
        final Document doc = Jsoup.parse(testSimpleTable);

        final TableScraper scraper = new TableScraper(doc);
        final List<Table> result = scraper.scrape();

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
        final List<Table> result = scraper.scrape();

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
        final List<Table> result = scraper.scrape();

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
        final List<Table> result = scraper.scrape();

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
        final List<Table> result = scraper.scrape();

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
        final List<Table> result = scraper.scrape();

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

    @Test
    public void testScrapeWikipediaWithNamesAsKeys() throws Exception {
        final Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_presidents_of_the_United_States_by_age").get();

        final TableScraper scraper = new TableScraper(doc);

        final Query<String> nameQuery = QueryBuilder.general().keyword("president").build();
        final Query<Date> birthQuery = QueryBuilder.date().keyword("birth").build();

        final Set<Pair<String, Date>> dataSet = scraper.handleQueryWithKey(nameQuery, birthQuery).asSet();

        boolean found = false;
        for (final Pair<String, Date> pair : dataSet) {
            // Check that at least jimmy carter's birthday is on the list, and that it get's properly matched with his name.
            if (pair.getKey().contains("Jimmy Carter")) {
                LocalDate localDate = pair.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (localDate.getMonth().equals(Month.OCTOBER) &&
                        localDate.getDayOfMonth() == 1 &&
                        localDate.getYear() == 1924) {
                    found = true;
                }
            }
        }

        assertTrue(found);
    }
}
