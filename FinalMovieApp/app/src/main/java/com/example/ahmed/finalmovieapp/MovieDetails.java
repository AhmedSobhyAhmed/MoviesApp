package com.example.ahmed.finalmovieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import Bean.Movie;


public class MovieDetails extends AppCompatActivity {

    Movie movie = new Movie();
    DetailsFragment detailsFragment;
    FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();

        String poster = intent.getStringExtra("poster");
        movie.setPoster(poster);
        String title = intent.getStringExtra("title");
        movie.setOriginalTitle(title);
        String date = intent.getStringExtra("date");
        movie.setReleaseDate(date);
        String rating = intent.getStringExtra("rating");
        movie.setAverageRating(rating);
        String overview = intent.getStringExtra("overview");
        movie.setOverView(overview);
        String id = intent.getStringExtra("id");
        movie.setId(id);


        manager = getSupportFragmentManager();
        detailsFragment = (DetailsFragment) manager.findFragmentById(R.id.fragment3);

        detailsFragment.setData(movie);

    }


}
