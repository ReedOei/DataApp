package com.reedoei.data.search;

import android.support.annotation.NonNull;

import com.google.common.collect.Lists;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by roei on 2/25/18.
 */
public class Searcher {
    private static final int MAX_ASYNC_TASKS = 5; // Don't make too many requests at once.

    private final List<Document> result = Collections.synchronizedList(new ArrayList<>());
    private final OkHttpClient client = new OkHttpClient();
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
}
