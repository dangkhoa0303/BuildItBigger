package com.example.android.builditbigger;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.Jokes;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public static String JOKE_APP = "jokeFromApp";
    public static String JOKE_GCE = "jokeFromGCE";
    public static String JOKE_PACKAGE = "jokePackage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
