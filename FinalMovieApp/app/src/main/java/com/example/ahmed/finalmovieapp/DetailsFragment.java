package com.example.ahmed.finalmovieapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Bean.Movie;
import Connectivity.URLConnection;

public class DetailsFragment extends Fragment {

    String apiKey = "?api_key=bf52996b695e866201b8cfc733d5ca88";

    ImageView poster;
    TextView title;
    TextView released;
    TextView rating;
    TextView overview;

    Movie movie = new Movie();

    ArrayList<String> favouritesId = new ArrayList<>();
    ArrayList<String> favouritesPoster = new ArrayList<>();
    ArrayList<String> favouritesTitle = new ArrayList<>();
    ArrayList<String> favouritesOverview = new ArrayList<>();
    ArrayList<String> favouritesRating = new ArrayList<>();
    ArrayList<String> favouritesReleasedDate = new ArrayList<>();

    StringBuilder movieId = new StringBuilder();
    StringBuilder moviePoster = new StringBuilder();
    StringBuilder movieTitle = new StringBuilder();
    StringBuilder movieOverview = new StringBuilder();
    StringBuilder movieReleased = new StringBuilder();
    StringBuilder movieRating = new StringBuilder();

    Button button;
    Button buttonReviews;
    Button buttonFavourite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        poster = (ImageView) view.findViewById(R.id.moviePoster);
        title = (TextView) view.findViewById(R.id.movieTitle);
        released = (TextView) view.findViewById(R.id.released);
        rating = (TextView) view.findViewById(R.id.rating);
        overview = (TextView) view.findViewById(R.id.overview);

        button = (Button) view.findViewById(R.id.buttonTrailers);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movie.getId() == null) {
                    Toast.makeText(getActivity(), "Please Select Movie First", Toast.LENGTH_LONG).show();
                } else {
                    String url = "http://api.themoviedb.org/3/movie/" + movie.getId() + "/videos" + apiKey;

                    BackgroundTrailers backgroundTrailers = new BackgroundTrailers();
                    backgroundTrailers.execute(url);

                }
            }
        });

        buttonReviews = (Button) view.findViewById(R.id.buttonReviews);
        buttonReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movie.getId() == null) {
                    Toast.makeText(getActivity(), "Please Select Movie First", Toast.LENGTH_LONG).show();
                } else {
                    String reviewsUrl = "http://api.themoviedb.org/3/movie/" + movie.getId() + "/reviews" + apiKey;

                    BackgroundReviews backgroundReviews = new BackgroundReviews();
                    backgroundReviews.execute(reviewsUrl);


                }
            }
        });

        buttonFavourite = (Button) view.findViewById(R.id.favo);
        buttonFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movie.getId() == null) {
                    Toast.makeText(getActivity(), "Please Select Movie First", Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("favourite", Context.MODE_PRIVATE);


                    String favouriteId = sharedPreferences.getString("ids", ",");
                    String favouritePoster = sharedPreferences.getString("posters", ",");
                    String favouriteTitle = sharedPreferences.getString("titles", ",");
                    String favouriteOverview = sharedPreferences.getString("overviews", ",");
                    String favouriteRDate = sharedPreferences.getString("dates", ",");
                    String favouriteRating = sharedPreferences.getString("ratings", ",");


                    favouritesId.add(movie.getId());
                    favouritesPoster.add(movie.getPoster());
                    favouritesTitle.add(movie.getOriginalTitle());
                    favouritesOverview.add(movie.getOverView());
                    favouritesRating.add(movie.getAverageRating());
                    favouritesReleasedDate.add(movie.getReleaseDate());

                    Boolean exist = false;
                    String[] check = favouriteId.split(",");
                    for (String aCheck : check) {
                        if (aCheck.equals(movie.getId())) {
                            exist = true;
                        }
                    }

                    if (!exist) {
                        for (String fmovieId : favouritesId) {
                            movieId.append(fmovieId).append(",");
                        }
                        movieId.append(favouriteId);

                        for (String fmoviePoster : favouritesPoster) {
                            moviePoster.append(fmoviePoster).append(",");
                        }
                        moviePoster.append(favouritePoster);

                        for (String fmovieTitle : favouritesTitle) {
                            movieTitle.append(fmovieTitle).append(",");
                        }
                        movieTitle.append(favouriteTitle);

                        for (String fmovieOverview : favouritesOverview) {
                            movieOverview.append(fmovieOverview).append(",");
                        }
                        movieOverview.append(favouriteOverview);

                        for (String fmovieReleased : favouritesReleasedDate) {
                            movieReleased.append(fmovieReleased).append(",");
                        }
                        movieReleased.append(favouriteRDate);

                        for (String fmovieRating : favouritesRating) {
                            movieRating.append(fmovieRating).append(",");
                        }
                        movieRating.append(favouriteRating);


                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("ids", movieId.toString());
                        editor.putString("posters", moviePoster.toString());
                        editor.putString("titles", movieTitle.toString());
                        editor.putString("overviews", movieOverview.toString());
                        editor.putString("ratings", movieRating.toString());
                        editor.putString("dates", movieReleased.toString());

                        editor.apply();

                        Toast.makeText(getActivity(), movie.getOriginalTitle() + " Added To Favourites", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), movie.getOriginalTitle() + " In Your Favourites", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void setData(Movie index) {

        movie = index;

        String image_url = "http://image.tmdb.org/t/p/w185" + index.getPoster();
        Picasso.with(getContext()).load(image_url).into(poster);
        title.setText(index.getOriginalTitle());
        released.setText(index.getReleaseDate());
        rating.setText(index.getAverageRating());
        overview.setText(index.getOverView());
    }


    public class BackgroundReviews extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            return URLConnection.getData(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            Intent intent = new Intent(getActivity(), ReviewsActivity.class);
            intent.putExtra("REVIEWS", result);
            startActivity(intent);

        }

    }


    public class BackgroundTrailers extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            return URLConnection.getData(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            Intent intent = new Intent(getActivity(), TrailersActivity.class);
            intent.putExtra("TRAILERS", result);
            startActivity(intent);

        }

    }
}
