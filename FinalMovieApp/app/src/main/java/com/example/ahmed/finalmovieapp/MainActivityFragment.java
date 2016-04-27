package com.example.ahmed.finalmovieapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapter.ImageAdapter;
import Bean.Movie;
import Connectivity.ParseMovies;
import Connectivity.URLConnection;


public class MainActivityFragment extends Fragment {

    GridView gridview;
    Spinner spinner;
    ImageAdapter adapter;

    List<Movie> moviesList = new ArrayList<>();
    List<Movie> fList;

    Movie movie;
    View view;
    Communicator communicator;

    String apiKey = "?api_key=bf52996b695e866201b8cfc733d5ca88";
    String orderType = "movie/popular";
    String url = "http://api.themoviedb.org/3/" + orderType + apiKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main, container, false);
        super.onCreate(savedInstanceState);

        spinner = (Spinner) view.findViewById(R.id.choices);
        final List<String> choices = new ArrayList<>();
        choices.add("Popular");
        choices.add("Top Rated");
        choices.add("Favourites");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, choices);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String choice = choices.get(position);

                if (choice.equals("Popular")) {
                    orderType = "movie/popular";
                    String url = "http://api.themoviedb.org/3/" + orderType + apiKey;
                    if (isOnline()) {
                        fetchData(url);
                        Toast.makeText(getActivity(), "Welcome To Popular Movies", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Please Connect The Internet", Toast.LENGTH_LONG).show();
                    }
                }

                if (choice.equals("Top Rated")) {
                    orderType = "movie/top_rated";
                    String url = "http://api.themoviedb.org/3/" + orderType + apiKey;
                    if (isOnline()) {
                        fetchData(url);
                        Toast.makeText(getActivity(), "Welcome To Top Rated Movies", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Please Connect The Internet", Toast.LENGTH_LONG).show();
                    }
                }

                if (choice.equals("Favourites")) {

                    if (isOnline()) {

                        String[] iid;
                        String[] poster;
                        String[] title;
                        String[] overview;
                        String[] date;
                        String[] rate;

                        SharedPreferences sharedPreference = getActivity().getSharedPreferences("favourite", Context.MODE_PRIVATE);

                        String favouriteId = sharedPreference.getString("ids", ",");
                        String favouritePoster = sharedPreference.getString("posters", ",");
                        String favouriteTitle = sharedPreference.getString("titles", ",");
                        String favouriteOverview = sharedPreference.getString("overviews", ",");
                        String favouriteRDate = sharedPreference.getString("dates", ",");
                        String favouriteRating = sharedPreference.getString("ratings", ",");

                        iid = favouriteId.split(",");
                        poster = favouritePoster.split(",");
                        title = favouriteTitle.split(",");
                        overview = favouriteOverview.split(",");
                        date = favouriteRDate.split(",");
                        rate = favouriteRating.split(",");


                        fList = new ArrayList<>();

                        for (int i = 0; i < iid.length; i++) {

                            Movie fMovie = new Movie();

                            fMovie.setId(iid[i]);
                            fMovie.setPoster(poster[i]);
                            fMovie.setOriginalTitle(title[i]);
                            fMovie.setOverView(overview[i]);
                            fMovie.setReleaseDate(date[i]);
                            fMovie.setAverageRating(rate[i]);

                            fList.add(fMovie);

                        }
                        if (fList.isEmpty()) {
                            spinner.setSelection(0);
                            Toast.makeText(getActivity(), "Nothing In Favourite ", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Welcome To Your Favourite Movies", Toast.LENGTH_LONG).show();

                            adapter = new ImageAdapter(getActivity(), fList);
                            gridview.setAdapter(adapter);

                            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View v,
                                                        int position, long id) {

                                    movie = fList.get(position);
                                    fList.clear();
                                    communicator.sendData(movie);

                                }
                            });
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please Connect The Internet", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getActivity(), "Welcome To Movies App", Toast.LENGTH_LONG).show();

                orderType = "movie/popular";
                String url = "http://api.themoviedb.org/3/" + orderType + apiKey;
                if (isOnline()) {
                    fetchData(url);
                } else {
                    Toast.makeText(getActivity(), "Please Connect The Internet", Toast.LENGTH_LONG).show();
                }
            }
        });

        gridview = (GridView) view.findViewById(R.id.gridview_movies);

        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void fetchData(String link) {
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(link);
    }

    public class BackgroundTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            return URLConnection.getData(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            moviesList = ParseMovies.parseJson(result);

            adapter = new ImageAdapter(getActivity(), moviesList);
            gridview.setAdapter(adapter);

            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    movie = moviesList.get(position);
                    communicator.sendData(movie);
                }
            });

        }

    }

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    public interface Communicator {
        void sendData(Movie movie);
    }

    public boolean isOnline() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}

