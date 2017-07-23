package android.example.com.news;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by mohanad on 03/07/17.
 */

public class QueryUtils {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Query the url and return an {@link News} object to represent a list of books.
     */
    public static ArrayList<News> fetchListOfNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }


        // Extract relevant fields from the JSON response and create an {@link News} object
        ArrayList<News> news = extractFeatureFromJson(jsonResponse);

        // Return the {@link News}
        return news;
    }

    /**
     * make a network request for a given url
     *
     * @param url : the news url
     * @return jsonString
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        //create http url connection
        HttpURLConnection urlConnection = null;
        //create inputsrtream
        InputStream inputStream = null;
        try {
            //open connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            //set the request method to GET
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //get the json inputstram
            inputStream = urlConnection.getInputStream();
            //convert inputstram to jsonString
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            Log.e(LOG_TAG, " error in make http request", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

                inputStream.close();
            }
        }

        return jsonResponse;
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder out = new StringBuilder();

        //check if inputsream is null
        if (inputStream != null) {
            //create InputStreamReader with Charset.forName("UTF-8")
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            //create BufferedReader
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            //reade line by line
            String line = bufferReader.readLine();
            while (line != null) {
                out.append(line);
                line = bufferReader.readLine();
            }
        }

        return out.toString();
    }

    /**
     * Return an {@link News} object by parsing out information
     * about the a list of books from the input NewsJSON string.
     */
    private static ArrayList<News> extractFeatureFromJson(String NewsJSON) {
        //the result list of books
        ArrayList<News> resultList = new ArrayList<>();

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(NewsJSON)) {
            return resultList;
        }

        try {
            //get the base json
            JSONObject baseJsonResponse = new JSONObject(NewsJSON);

            JSONObject response = baseJsonResponse.getJSONObject("response");
            //get the result josnArray
            JSONArray resultsArray = response.getJSONArray("results");

            JSONObject currentNews;
            String newsTitle, newsSection, newsWebUrl;

            //iterate and get the news data
            for (int i = 0; i < resultsArray.length(); ++i) {
                //get the ith news
                currentNews = resultsArray.getJSONObject(i);

                //get the news title,section and webUrl
                newsTitle = currentNews.getString("webTitle");
                newsSection = currentNews.getString("sectionName");
                newsWebUrl = currentNews.getString("webUrl");

                resultList.add(new News(newsTitle, newsSection, newsWebUrl));

            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "error parsing news json", e);
        }

        return resultList;
    }
}