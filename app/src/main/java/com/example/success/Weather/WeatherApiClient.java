package com.example.success.Weather;

import com.example.success.Weather.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherApiClient {
    private static final String API_KEY = "3c536d16d0062331296622d7f54339fb";

    public static void getCurrentWeather(String city, final WeatherCallback callback) {
        WeatherApiService apiService = RetrofitClient.getRetrofitInstance().create(WeatherApiService.class);

        Call<WeatherResponse> call = apiService.getCurrentWeather(city, API_KEY);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        String weatherDescription = weatherResponse.getWeather().get(0).getMain();
                        double temperature = weatherResponse.getMainInfo().getTemperature();

                        // 回调结果
                        callback.onSuccess(weatherDescription, temperature);
                    } else {
                        callback.onFailure("No weather data available");
                    }
                } else {
                    callback.onFailure("Request failed");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface WeatherCallback {
        void onSuccess(String weatherDescription, double temperature);

        void onFailure(String errorMessage);
    }
}