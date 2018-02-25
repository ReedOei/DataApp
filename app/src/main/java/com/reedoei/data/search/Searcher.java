package com.reedoei.data.search;

import android.support.annotation.NonNull;

import com.google.common.collect.Lists;
import com.reedoei.data.data.DataSet;
import com.reedoei.data.scraping.query.DocumentScraper;
import com.reedoei.data.scraping.query.InvalidQueryException;
import com.reedoei.data.scraping.query.Query;
import com.reedoei.data.scraping.query.Queryable;
import com.reedoei.data.scraping.query.TableScraper;

import org.apache.commons.math3.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by roei on 2/25/18.
 */
public class Searcher implements Queryable {
    private static final int MAX_ASYNC_TASKS = 3; // Don't make too many requests at once.

    private final List<Document> result = Collections.synchronizedList(new ArrayList<>());
    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .build();
    private final List<String> allUrls;

    public Searcher(final Search search) {

        allUrls = search.findURLs();
    }

    @NonNull
    public List<Document> search() {
        if (!result.isEmpty()) {
            return result;
        }

        final List<Thread> threads = new ArrayList<>();
        for (final List<String> urlList : Lists.partition(allUrls, allUrls.size() / MAX_ASYNC_TASKS)) {
            threads.add(new Thread(() -> {
                for (final String url : urlList) {
                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    try {
                        final Response response = client.newCall(request).execute();

                        if (response.isSuccessful() && response.body() != null) {
                            try {
                                result.add(Jsoup.parse(response.body().string()));
                            } catch (IOException ignored) {}
                            response.body().close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }));

            threads.get(threads.size() - 1).start();
        }

        for (final Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ignored) {}
        }

        return result;
    }

    @NonNull
    @Override
    public <T> DataSet<T> handleQuery(Query<T> query) throws InvalidQueryException {
        DataSet<T> result = DataSet.empty();

        int n = 0;
        for (final Document doc : search()) {
            n++;
            System.out.println("Scraping document " + n + " of " + search().size());
            final DocumentScraper scraper = new DocumentScraper(doc);

            System.out.println("Querying document " + n + " of " + search().size());
            final DataSet<T> temp = scraper.handleQuery(query);

            if (temp.getScore() > result.getScore()) {
                result = temp;
            }

            System.out.println("Document has " + temp.getScore() + " score and " + temp.asSet().size() + " entries.");
            System.out.println("Best dataset has " + result.getScore() + " score and " + result.asSet().size() + " entries (" + result.asSet() + ")");
        }

        return result;
    }

    @NonNull
    @Override
    public <K, V> DataSet<Pair<K, V>> handleQueryWithKey(Query<K> keyQuery, Query<V> valueQuery) throws InvalidQueryException {
        DataSet<Pair<K,V>> result = DataSet.empty();

        for (final Document doc : search()) {
            final TableScraper scraper = new TableScraper(doc);
            final DataSet<Pair<K,V>> temp = scraper.handleQueryWithKey(keyQuery, valueQuery);

            if (temp.getScore() > result.getScore()) {
                result = temp;
            }
        }

        return result;
    }
}
