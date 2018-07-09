package com.example.pm06mnx.lesson12.utils;

import com.example.pm06mnx.lesson12.service.OpenWeatherService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    public static OpenWeatherService getOpenWeatherService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(OpenWeatherService.class);
    }

}
