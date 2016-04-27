package Connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by AhMed on 3/24/2016.
 */
public class URLConnection {


    public static String getData(String uri) {

        BufferedReader reader = null;

        try {

            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringContent.append(line + "\n");
            }
            return stringContent.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
