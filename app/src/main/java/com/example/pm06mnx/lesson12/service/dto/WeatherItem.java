package com.example.pm06mnx.lesson12.service.dto;

import com.example.pm06mnx.lesson12.utils.UnixTimeToDateAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Данные по погоде в момент времени
 */
public class WeatherItem {

    @SerializedName("dt")
    @JsonAdapter(UnixTimeToDateAdapter.class)
    private Date date;
    private MainWeatherData main;;
    private WindData wind;
    private List<WeatherData> weather;

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

    public List<WeatherData> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherData> weather) {
        this.weather = weather;
    }
}
