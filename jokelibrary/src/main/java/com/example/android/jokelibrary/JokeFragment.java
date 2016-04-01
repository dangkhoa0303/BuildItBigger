package com.example.android.jokelibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.Jokes;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Dell on 4/2/2016.
 */
public class JokeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.joke_fragment, container, false);

        Intent i = getActivity().getIntent();

        String jokeTxt = i.getStringExtra("joke");

        final TextView jokeText = (TextView) view.findViewById(R.id.jokeDisplay);

        jokeText.setText(jokeTxt);

        Button toastBtn = (Button) view.findViewById(R.id.toastButton);

        toastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Jokes jokes = new Jokes();

                Toast.makeText(getContext(), jokes.getJoke(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
