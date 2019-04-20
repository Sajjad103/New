package com.sajjad.assesment.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sajjad.assesment.Common.Common;
import com.sajjad.assesment.Model.Main;
import com.sajjad.assesment.Model.Sys;
import com.sajjad.assesment.Model.Weather;
import com.sajjad.assesment.Model.WeatherModel;
import com.sajjad.assesment.Model.WeatherResult;
import com.sajjad.assesment.Model.Wind;
import com.sajjad.assesment.MySingleton;
import com.sajjad.assesment.R;
import com.sajjad.assesment.Retrofit.IOpenWeatherMap;
import com.sajjad.assesment.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

import static com.android.volley.VolleyLog.TAG;

public class WeatherDisplayFragment extends Fragment {

    static WeatherDisplayFragment instance;
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;
    int mStatusCode;
    private String jsonResponse;
    ImageView weather_iv;
    TextView country_tv,description_tv,temperature_tv,humidity_tv,wind_tv;

    private String urlJson = "http://api.openweathermap.org/data/2.5/weather?lat=" +
            Common.current_location.getLatitude() +
            "&lon=" +
            Common.current_location.getLongitude() +
            "&appid=" +
            Common.API_ID;


    public static WeatherDisplayFragment getInstance(){
        if (instance == null)
            instance = new WeatherDisplayFragment();
        return instance;
    }

    public WeatherDisplayFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);

    }
    Context ctx;
    @Override
    public void onAttach(Context context) {
        ctx = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_weather_display,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        country_tv = (TextView)view.findViewById(R.id.country);
        description_tv = (TextView)view.findViewById(R.id.description);
        humidity_tv = (TextView)view.findViewById(R.id.humidity);
        wind_tv = (TextView)view.findViewById(R.id.wind);
        temperature_tv = (TextView)view.findViewById(R.id.temperature);

        weather_iv = (ImageView)view.findViewById(R.id.weather_image);


        makeJsonRequest();
        super.onViewCreated(view, savedInstanceState);
    }


    String countryName,description,icon,temperature,humidity,wind;
    private void makeJsonRequest() {

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJson, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    WeatherModel weatherModel = new WeatherModel();
                    JSONArray array = response.getJSONArray("weather");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject weatherJson = array.getJSONObject(i);

                        weatherModel.setDescription(weatherJson.getString("description"));
                        weatherModel.setIcon(weatherJson.getString("icon"));

//                        description = weatherJson.getString("description");
//                        icon = weatherJson.getString("icon");
                    }

                    JSONObject sysJson = response.getJSONObject("sys");
                    weatherModel.setCountry(sysJson.getString("country"));
//                    countryName= sysJson.getString("country");

                    JSONObject mainJson = response.getJSONObject("main");
                    weatherModel.setCountry(mainJson.getString("temp"));
                    weatherModel.setHumidity(mainJson.getString("humidity"));

//                    temperature =  mainJson.getString("temp");
//                    humidity = mainJson.getString("humidity");

                    JSONObject windJson = response.getJSONObject("wind");
                    weatherModel.setWind(windJson.getString("speed"));
                    displayWeatherInfo(weatherModel);
//                    wind = windJson.getString("speed");

//                    setWeatherInfo();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error: Check the Connection" + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: Check the Connection" + error.getMessage());
                Toast.makeText(getContext(),
                        "Error: Check the Connection", Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        MySingleton.getInstance(getContext()).addToRequestque(jsonObjReq);
    }

    void displayWeatherInfo(WeatherModel weatherModel)
    {
        Picasso.with(getContext())
                .load(new StringBuilder("https://openweathermap.org/img/w/")
                        .append(weatherModel.getIcon()).append(".png").toString())
                .into(weather_iv);

        country_tv.setText(weatherModel.getCountry());
        description_tv.setText(weatherModel.getDescription());
        temperature_tv.setText(weatherModel.getTemperature());
        humidity_tv.setText(weatherModel.getHumidity());
        wind_tv.setText(weatherModel.getWind());

    }

    void setWeatherInfo()
    {
        Picasso.with(getContext())
                .load(new StringBuilder("https://openweathermap.org/img/w/")
                        .append(icon).append(".png").toString())
                .into(weather_iv);

        country_tv.setText(countryName);
        description_tv.setText(description);
        temperature_tv.setText(temperature);
        humidity_tv.setText(humidity);
        wind_tv.setText(wind);

    }

}
