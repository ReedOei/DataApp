package com.reedoei.data.scraping.scrapers;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by roei on 2/24/18.
 */

public class DateScraper extends AbstractScraper<Date> {
    private static final double KEYWORD_MULTIPLIER = 2.0;

    private static final Map<Pattern, SimpleDateFormat> patternMap = new HashMap<>();

    static {
        patternMap.put(Pattern.compile("([0-9]+-[0-9]{1,2}-[0-9]{1,2})"),
                       new SimpleDateFormat("yyyy-MM-dd"));

        final String months =
                "(?:January|Jan|" +
                "February|Feb|" +
                "March|Mar|" +
                "April|Apr|" +
                "May|" +
                "June|Jun|" +
                "July|Jul|" +
                "August|Aug|" +
                "September|Sept|" +
                "October|Oct|" +
                "November|Nov|" +
                "December|Dec)";

        patternMap.put(Pattern.compile("(" + months + "\\.? [0-9]{1,2}, [0-9]+)"),
                new SimpleDateFormat("MMM dd, yyyy"));
    }

    public DateScraper(List<String> keywords) {
        super(keywords);
    }

    @Override
    public double getScore(final String text) {
        double total = scrapeData(text).size() + getKeywordScore(text);

        if (text.toLowerCase().contains("date") || text.toLowerCase().contains("year") || text.toLowerCase().contains("day")) {
            if (total == 0) {
                total = 1;
            } else {
                total *= KEYWORD_MULTIPLIER;
            }
        }

        return total;
    }

    @NonNull
    @Override
    public Set<Date> scrapeData(String text) {
        final Set<Date> result = new HashSet<>();

        for (final Map.Entry<Pattern, SimpleDateFormat> entry : patternMap.entrySet()) {
            final SimpleDateFormat format = entry.getValue();

            final List<Map<Integer, String>> matches = ScraperUtils.findAll(entry.getKey(), text);

            for (final Map<Integer, String> m : matches) {
                for (final String val : m.values()) {
                    try {
                        result.add(format.parse(val));
                    } catch (ParseException ignored) {}
                }
            }
        }

        return result;
    }
}
