package com.example.ahmed.finalmovieapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import Bean.Movie;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Communicator {

    DetailsFragment detailsFragment;
    FragmentManager manager;
    MainActivityFragment mainActivityFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manager = getSupportFragmentManager();
        mainActivityFragment = (MainActivityFragment) manager.findFragmentById(R.id.fragment);

        mainActivityFragment.setCommunicator(MainActivity.this);
    }

    @Override
    public void sendData(Movie index) {

        manager = getSupportFragmentManager();
        detailsFragment = (DetailsFragment) manager.findFragmentById(R.id.fragment3);

        if (detailsFragment != null && detailsFragment.isVisible()) {
            detailsFragment.setData(index);
        } else {

            Intent intent = new Intent(MainActivity.this, MovieDetails.class);
            intent.putExtra("poster", index.getPoster());
            intent.putExtra("title", index.getOriginalTitle());
            intent.putExtra("date", index.getReleaseDate());
            intent.putExtra("rating", index.getAverageRating());
            intent.putExtra("overview", index.getOverView());
            intent.putExtra("id", index.getId());
            startActivity(intent);

        }
    }

}
