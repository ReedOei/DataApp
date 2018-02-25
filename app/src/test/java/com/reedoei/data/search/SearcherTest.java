package com.reedoei.data.search;

import com.reedoei.data.scraping.query.DataSet;
import com.reedoei.data.scraping.query.InvalidQueryException;
import com.reedoei.data.scraping.query.Query;
import com.reedoei.data.scraping.query.QueryBuilder;

import org.apache.commons.math3.util.Pair;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @Test
    public void testSearchPresidentBirthday() throws InvalidQueryException {
        Searcher searcher = new Searcher(new Search("list", "of", "president's", "birthdays"));
        final Set<Date> dataSet = searcher.handleQuery(QueryBuilder.date().keyword("birth").build()).asSet();

        boolean found = false;
        for (final Date date : dataSet) {
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Check that at least james k polk' birthday is on the list.
            if (localDate.getMonth().equals(Month.NOVEMBER) &&
                    localDate.getDayOfMonth() == 2 &&
                    localDate.getYear() == 1795) {
                found = true;
            }
        }

        assertTrue(found);
    }
}