package com.example.myviewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<WeatherItems>> listWeather = new MutableLiveData<>();

    void setWeather(final String cities){
        final ArrayList<WeatherItems> listItems = new ArrayList<>();

        String API_KEY = "YOUR_API_KEY";
        String URL = "https://api.openweathermap.org/data/2.5/group?id=" + cities + "&units=metric&appid=" + API_KEY;

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    String result = new String(responseBody);

                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject weather = jsonArray.getJSONObject(i);
                        WeatherItems weatherItems = new WeatherItems();
                        weatherItems.setId(weather.getInt("id"));
                        weatherItems.setName(weather.getString("name"));
                        weatherItems.setCurrentWeather(weather.getJSONArray("weather").getJSONObject(0).getString("main"));
                        weatherItems.setDescription(weather.getJSONArray("weather").getJSONObject(0).getString("description"));
                        double tempInKelvin = weather.getJSONObject("main").getDouble("temp");
                        double tempInCelsius = tempInKelvin - 273;
                        weatherItems.setTemperature(new DecimalFormat("##.##").format(tempInCelsius));
                        listItems.add(weatherItems);
                    }
                    listWeather.postValue(listItems);
                } catch (Exception e) {
                    Log.d("onFailure", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    LiveData<ArrayList<WeatherItems>> getWeather(){
        return listWeather;
    }
}
