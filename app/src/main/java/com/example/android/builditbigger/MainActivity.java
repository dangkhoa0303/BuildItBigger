package com.example.android.builditbigger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.Jokes;

public class MainActivity extends AppCompatActivity {

    public static String JOKE_KEY = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
