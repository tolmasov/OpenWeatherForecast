package com.example.pm06mnx.lesson12.service.dto;

import com.example.pm06mnx.lesson12.utils.LongToDateAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Данные по погоде в момент времени
 */
public class WeatherItem {

    @SerializedName("dt")
    @JsonAdapter(LongToDateAdapter.class)
    private Date date;
    private MainWeatherData main;
    private WindData wind;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MainWeatherData getMain() {
        return main;
    }

    public void setMain(MainWeatherData main) {
        this.main = main;
    }

    public WindData getWind() {
        return wind;
    }

    public void setWind(WindData wind) {
        this.wind = wind;
    }
}
