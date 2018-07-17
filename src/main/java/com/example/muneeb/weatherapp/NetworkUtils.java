package com.example.muneeb.weatherapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private final static String TAG = "NetworkUtils";

    private final static String WEATHER_URL = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/261158";

    private final static String API_KEY = "SAMEBJkJSGFm7BEgSGN7cIvXV78AsnuQ";

    private final static String PARAM_API_KEY = "apikey";

    private final static String METRIC_VALUE = "true";

    private final static String PARAM_METRIC = "metric";

    public static URL buildUrlForWeather(){
        Uri buildUri = Uri.parse(WEATHER_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_METRIC, METRIC_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "buildUrlForWeather: URL: "+url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
