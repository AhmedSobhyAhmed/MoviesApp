package Connectivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Bean.Movie;


public class ParseMovies {

    public static List<Movie> parseJson(String content) {

        try {

            JSONObject sonObect = new JSONObject(content);
            JSONArray jsonArray = sonObect.getJSONArray("results");
            List<Movie> movieList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Movie movie = new Movie();

                movie.setPoster(jsonObject.getString("poster_path"));
                movie.setOverView(jsonObject.getString("overview"));
                movie.setReleaseDate(jsonObject.getString("release_date"));
                movie.setOriginalTitle(jsonObject.getString("original_title"));
                movie.setAverageRating(jsonObject.getString("vote_average"));
                movie.setId(jsonObject.getString("id"));

                movieList.add(movie);
            }

            return movieList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}



