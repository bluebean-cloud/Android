package com.example.success.Weather;

import com.google.gson.annotations.SerializedName;

public class WeatherInfo {
    @SerializedName("main")
    private String main;

    public String getMain() {
        return main;
    }
}
