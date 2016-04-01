package com.example.android.builditbigger;


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
import com.example.android.jokelibrary.JokeActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Dell on 4/1/2016.
 */

public class FragmentMain extends Fragment {

    static class ViewHolder {
        @InjectView(R.id.tellJokeButton)
        Button tellJokeBtn;

        @InjectView(R.id.jokeText)
        TextView jokeTextView;

        @InjectView(R.id.launchButton)
        Button launchButton;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.tellJokeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Jokes jokes = new Jokes();

                Toast.makeText(getContext(), jokes.getJoke(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), JokeActivity.class);
                i.putExtra(MainActivity.JOKE_KEY, viewHolder.jokeTextView.getText().toString());
                startActivity(i);
            }
        });

        return view;
    }

}
