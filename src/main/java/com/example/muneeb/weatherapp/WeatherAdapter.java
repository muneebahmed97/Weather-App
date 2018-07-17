package com.example.muneeb.weatherapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends ArrayAdapter<Weather> {
    public WeatherAdapter(@NonNull Context context, ArrayList<Weather> weatherArrayList) {
        super(context, 0, weatherArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Weather weather = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView tvDate = convertView.findViewById(R.id.tv_date);
        TextView tvMaxTemp = convertView.findViewById(R.id.tv_maxTemp);
        TextView tvMinTemp = convertView.findViewById(R.id.tv_minTemp);
        TextView tvLink = convertView.findViewById(R.id.tv_link);

        tvDate.setText(weather.getDate());
        tvMaxTemp.setText(weather.getMaxTemp());
        tvMinTemp.setText(weather.getMinTemp());
        tvLink.setText(weather.getLink());

        return convertView;
    }
}
