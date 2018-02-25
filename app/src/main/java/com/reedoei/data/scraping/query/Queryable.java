package com.reedoei.data.scraping.query;

import java.util.Set;

/**
 * Created by roei on 2/24/18.
 */

public abstract class Queryable {
    public abstract Set<Data> query(final Query query);
}
