package com.example.muneeb.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName() ;
    private ArrayList<Weather> weatherArrayList = new ArrayList<>();
    private ListView lvListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvListView = (ListView) findViewById(R.id.lv_list);

        URL weatherUrl = NetworkUtils.buildUrlForWeather();
        new GetWeatherDetails().execute(weatherUrl);
        Log.i(TAG,"onCreate: Weather URL: " + weatherUrl);
    }

    public class GetWeatherDetails extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL weatherURL = urls[0];
            String weatherSearchResults = null;

            try {
                weatherSearchResults = NetworkUtils.getResponseFromHttpUrl(weatherURL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.i(TAG, "doInBackground: Weather Search Results: " + weatherSearchResults);
            return weatherSearchResults;
        }

        @Override
        protected void onPostExecute(String weatherSearchResult) {
            if (weatherSearchResult != null && !weatherSearchResult.equals("")) {
                weatherArrayList = parseJSON(weatherSearchResult);

                Iterator iterator = weatherArrayList.iterator();
                while (iterator.hasNext()) {
                    Weather weatherIterator = (Weather) iterator.next();

                    Log.i(TAG, "onPostExecute: Date: " + weatherIterator.getDate() +
                            " Min Temperature: " + weatherIterator.getMinTemp() +
                            " Max Temperature: " + weatherIterator.getMaxTemp() +
                            " Link: " + weatherIterator.getLink());
                }
            }
            super.onPostExecute(weatherSearchResult);
        }
    }

    private ArrayList<Weather> parseJSON(String weatherSearchResult) {
        if (weatherArrayList != null) {
            weatherArrayList.clear();
        }

        if (weatherSearchResult != null) {
            try {
                JSONObject JsonObject = new JSONObject(weatherSearchResult);
                JSONArray resultArray = JsonObject.getJSONArray("DailyForecasts");

                for (int i = 0; i < resultArray.length(); i++) {
                    Weather weather = new Weather();

                    JSONObject resultObj = resultArray.getJSONObject(i);

                    String date = resultObj.getString("Date");
                    weather.setDate(date);

                    JSONObject temperatureObj = resultObj.getJSONObject("Temperature");

                    String minTemp = temperatureObj.getJSONObject("Minimum").getString("Value");
                    weather.setMinTemp(minTemp);

                    String maxTemp = temperatureObj.getJSONObject("Maximum").getString("Value");
                    weather.setMaxTemp(maxTemp);

                    String link = resultObj.getString("Link");
                    weather.setLink(link);

//                        Log.i(TAG,"parseJSON: Date: " + date + " " +
//                                "Minimum Temperature: " + minTemp + " " +
//                                "Maximum Temperature: " + maxTemp + " " +
//                                "Link: " + link);

                    weatherArrayList.add(weather);
                }

                if (weatherArrayList != null) {
                    WeatherAdapter weatherAdapter = new WeatherAdapter(this, weatherArrayList);
                    lvListView.setAdapter(weatherAdapter);
                }

                return weatherArrayList;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}