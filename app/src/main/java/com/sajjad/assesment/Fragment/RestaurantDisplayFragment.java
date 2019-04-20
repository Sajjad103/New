package com.sajjad.assesment.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sajjad.assesment.R;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class RestaurantDisplayFragment extends Fragment {

    String API_KEY = "AIzaSyAKBVnPyXNsz-ttcAaCvKeaqmZy4wLL5zw";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";

    private static String Full_PLACES_API = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=AIzaSyAKBVnPyXNsz-ttcAaCvKeaqmZy4wLL5zw";

    String API_Name = "API key 1";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_weather_display,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}

