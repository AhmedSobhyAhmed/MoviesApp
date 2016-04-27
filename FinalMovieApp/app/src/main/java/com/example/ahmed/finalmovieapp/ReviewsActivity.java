package com.example.ahmed.finalmovieapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Adapter.ReviewAdapter;
import Bean.Review;
import Connectivity.ParseReviews;


public class ReviewsActivity extends Activity {

    ListView listView;
    List<Review> reviews = new ArrayList<>();
    ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        listView = (ListView) findViewById(R.id.reviews);

        Intent intent = getIntent();
        String result = intent.getStringExtra("REVIEWS");

        reviews = ParseReviews.parseJson(result);

        reviewAdapter = new ReviewAdapter(this, reviews);
        listView.setAdapter(reviewAdapter);
    }
}
