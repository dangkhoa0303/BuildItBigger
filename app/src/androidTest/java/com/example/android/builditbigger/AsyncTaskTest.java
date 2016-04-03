package com.example.android.builditbigger;

import android.test.AndroidTestCase;

import com.example.android.Jokes;

import java.util.concurrent.ExecutionException;

/**
 * Created by Dell on 4/3/2016.
 */
public class AsyncTaskTest extends AndroidTestCase {

    EndpointsAsyncTask task;
    String joke = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        task = new EndpointsAsyncTask(getContext());

    }

    public void testDownload() throws InterruptedException {
        try {
            // get joke from GCE
            joke = task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // compare the joke from GCE with the initial joke from Java Library
        assertEquals(new Jokes().pullJoke(), joke);
    }

}
