package Connectivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Bean.Trailer;

public class ParseTrailers {

    public static List<Trailer> parseJson(String content) {


        try {

            JSONObject sonObect = new JSONObject(content);
            JSONArray jsonArray = sonObect.getJSONArray("results");
            List<Trailer> trailers = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Trailer trailer = new Trailer();

                trailer.setId(jsonObject.getString("id"));
                trailer.setKey(jsonObject.getString("key"));
                trailer.setName(jsonObject.getString("name"));
                trailer.setSite(jsonObject.getString("site"));
                trailer.setType(jsonObject.getString("type"));

                trailers.add(trailer);
            }

            return trailers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
