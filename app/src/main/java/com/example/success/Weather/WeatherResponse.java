package com.example.success.Weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("weather")
    private List<WeatherInfo> weather;

    @SerializedName("main")
    private MainInfo mainInfo;

    public List<WeatherInfo> getWeather() {
        return weather;
    }

    public MainInfo getMainInfo() {
        return mainInfo;
    }
}

