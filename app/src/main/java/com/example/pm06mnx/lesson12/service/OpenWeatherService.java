package com.example.pm06mnx.lesson12.service;

import com.example.pm06mnx.lesson12.service.dto.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {

    @GET("/data/2.5/forecast?mode=json&lang=ru&units=metric")
    Call<Forecast> getForecast(@Query("q") String query, @Query("appid") String apiKey);
}
