package com.example.ahmed.finalmovieapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import Adapter.TrailerAdapter;
import Bean.Trailer;
import Connectivity.ParseTrailers;


public class TrailersActivity extends Activity {

    GridView gridView;
    List<Trailer> trailers = new ArrayList<>();
    TrailerAdapter trailerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailers);

        gridView = (GridView) findViewById(R.id.gridView);

        Intent intent = getIntent();
        String result = intent.getStringExtra("TRAILERS");

        trailers = ParseTrailers.parseJson(result);

        trailerAdapter = new TrailerAdapter(this, trailers);
        gridView.setAdapter(trailerAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Trailer test = trailers.get(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + test.getKey()));
                startActivity(intent);

            }
        });
    }
}
