package com.reedoei.data.scraping.scrapers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by roei on 2/24/18.
 */
public class ScraperUtils {
    public static List<Map<Integer, String>> findAll(final Pattern pattern, final String text) {
        final Matcher m = pattern.matcher(text);

        final List<Map<Integer, String>> result = new ArrayList<>();

        while (m.find()) {
            final Map<Integer, String> groupMap = new HashMap<>();

            for (int group = 0; group < m.groupCount(); group++) {
                groupMap.put(group, m.group(group));
            }

            result.add(groupMap);
        }

        return result;
    }
}
