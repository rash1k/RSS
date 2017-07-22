package com.example.rash1k.rss.network;

import android.net.Uri;

import com.example.rash1k.rss.model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsFetch {

    private static final String TAG = NewsFetch.class.getSimpleName();

    public ArrayList<NewsItem> fetchItems() {
        String url = Uri.parse("https://api.flickr.com/services/feeds/photos_public.gne")
                .buildUpon()
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .build().toString();


        ArrayList<NewsItem> items = new ArrayList<>();
        try {
            parseItems(items, getJsonObject(url));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return items;
    }


    private JSONObject getJsonObject(String urlSpec) {
        HttpURLConnection connection = null;
        InputStream in = null;

        try {
            URL url = new URL(urlSpec);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            StringBuilder builder = new StringBuilder();
            in = connection.getInputStream();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    builder.append(line)
                            .append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new JSONObject(builder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void parseItems(List<NewsItem> items, JSONObject jsonBody) throws JSONException {
//        JSONObject titleObject = jsonBody.getJSONObject("title");
        JSONArray itemsJsonArray = jsonBody.getJSONArray("items");

        for (int i = 0; i < itemsJsonArray.length(); i++) {
            JSONObject itemJsonObject = itemsJsonArray.getJSONObject(i);
            JSONObject mediaJsonObject = itemJsonObject.getJSONObject("media");

            NewsItem newsItem = new NewsItem();

            newsItem.setTitle(itemJsonObject.getString("title"));
            newsItem.setUrlPhotos(mediaJsonObject.getString("m"));
            newsItem.setDatePublished(itemJsonObject.getString("published"));
            newsItem.setAuthorName(itemJsonObject.getString("author"));
            newsItem.setAuthorId(itemJsonObject.getString("author_id"));

            items.add(newsItem);
        }
    }
}
