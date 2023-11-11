package com.example.success.Weather;

import com.google.gson.annotations.SerializedName;

public class MainInfo {
    @SerializedName("temp")
    private double temperature;

    public double getTemperature() {
        return temperature;
    }
}
