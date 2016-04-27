package Connectivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Bean.Review;

/**
 * Created by AhMed on 4/25/2016.
 */
public class ParseReviews {

    public static List<Review> parseJson(String content) {


        try {

            JSONObject sonObect = new JSONObject(content);
            JSONArray jsonArray = sonObect.getJSONArray("results");
            List<Review> trailers = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Review review = new Review();

                review.setId(jsonObject.getString("id"));
                review.setAuthor(jsonObject.getString("author"));
                review.setContent(jsonObject.getString("content"));

                trailers.add(review);
            }

            return trailers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
