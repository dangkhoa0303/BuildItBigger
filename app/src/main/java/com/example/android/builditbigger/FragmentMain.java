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

import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Dell on 4/1/2016.
 */

public class FragmentMain extends Fragment {

    private String resultFromGCE = null;

    static class ViewHolder {
        @InjectView(R.id.tellJokeButton)
        Button tellJokeBtn;

        @InjectView(R.id.jokeText)
        TextView jokeTextView;

        @InjectView(R.id.launchButton)
        Button launchButton;

        @InjectView(R.id.kickOff)
        Button kickOffButton;

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
                if (resultFromGCE != null) {
                    Intent i = new Intent(getActivity(), JokeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(MainActivity.JOKE_APP, viewHolder.jokeTextView.getText().toString());
                    bundle.putString(MainActivity.JOKE_GCE, resultFromGCE);
                    i.putExtra(MainActivity.JOKE_PACKAGE, bundle);
                    startActivity(i);
                }
                else {
                    String inform = "No joke from GCE !!! Please press the -RETRIEVE JOKE FROM GCE- BUTTON first";
                    Toast.makeText(getContext(), inform, Toast.LENGTH_LONG).show();
                }
            }
        });

        viewHolder.kickOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    resultFromGCE = new EndpointsAsyncTask().execute(getActivity()).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

}
