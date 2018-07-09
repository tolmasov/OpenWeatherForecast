package com.example.pm06mnx.lesson12.service.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Данные о прогнозе погоды
 */
public class Forecast {

    @SerializedName("cnt")
    private Integer count;
    private List<WeatherItem> list;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<WeatherItem> getList() {
        return list;
    }

    public void setList(List<WeatherItem> list) {
        this.list = list;
    }
}
