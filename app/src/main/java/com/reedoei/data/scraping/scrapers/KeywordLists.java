package com.reedoei.data.scraping.scrapers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */

public class KeywordLists {
    public static final Map<String, Set<String>> lists = new HashMap<>();

    static {
        lists.put("birth", new HashSet<>(Arrays.asList(
                "born", "birthday"
        )));

        lists.put("death", new HashSet<>(Arrays.asList(
                "die", "died"
        )));
    }
}
