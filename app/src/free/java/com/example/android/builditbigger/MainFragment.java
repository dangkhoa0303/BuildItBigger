package com.example.android.builditbigger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.Jokes;
import com.example.android.jokelibrary.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Dell on 4/1/2016.
 */

public class MainFragment extends Fragment {

    private String resultFromGCE = null;
    private String SAVE_RESULT = "JOKE_FROM_GCE";
    private SharedPreferences sharedPreferences;
    private InterstitialAd mInterstitialAd;

    public static ProgressBar indicator;

    public static class ViewHolder {
        @InjectView(R.id.tellJokeButton)
        Button tellJokeBtn;

        @InjectView(R.id.jokeText)
        TextView jokeTextView;

        @InjectView(R.id.launchButton)
        Button launchButton;

        @InjectView(R.id.kickOff)
        Button kickOffButton;

        @InjectView(R.id.adView)
        AdView mAdView;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    // load new Ad
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences(null, Context.MODE_PRIVATE);
        resultFromGCE = sharedPreferences.getString(SAVE_RESULT, null);

        mInterstitialAd = new InterstitialAd(getActivity());
        // set unit id for this ad
        mInterstitialAd.setAdUnitId("ca-app-pub-6978009773705136/1321517206");

        // detect when the Ad is closed
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                requestNewInterstitial();
                tellJoke();
            }
        });
        requestNewInterstitial();
    }

    private void tellJoke() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(new Jokes().getJoke());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void launchAndroidLib(String jokeApp) {
        Intent i = new Intent(getActivity(), JokeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.JOKE_APP, jokeApp);
        bundle.putString(MainActivity.JOKE_GCE, resultFromGCE);
        i.putExtra(MainActivity.JOKE_PACKAGE, bundle);
        startActivity(i);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        indicator = (ProgressBar) view.findViewById(R.id.progressBar);
        indicator.setVisibility(View.INVISIBLE);

        viewHolder.tellJokeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    tellJoke();
                }
            }
        });

        viewHolder.launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resultFromGCE != null) {
                    String jokeApp = viewHolder.jokeTextView.getText().toString();
                    launchAndroidLib(jokeApp);
                } else {
                    String inform = "No joke from GCE !!! Please press the -RETRIEVE JOKE FROM GCE- BUTTON first";
                    Toast.makeText(getContext(), inform, Toast.LENGTH_LONG).show();
                }
            }
        });

        viewHolder.kickOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EndpointsAsyncTask task = new EndpointsAsyncTask(getActivity());
                    indicator.setVisibility(View.VISIBLE);
                    resultFromGCE = task.execute().get();

                    String jokeApp = viewHolder.jokeTextView.getText().toString();
                    launchAndroidLib(jokeApp);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        // request and load Ad into ad banner
        AdRequest adRequest = new AdRequest.Builder().build();
        viewHolder.mAdView.loadAd(adRequest);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        // save resultFromGCE
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (resultFromGCE != null) {
            editor.putString(SAVE_RESULT, resultFromGCE);
            editor.commit();
        }
    }
}
