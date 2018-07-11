package com.example.pm06mnx.lesson12.list.dto;

import java.util.Collection;
import java.util.Date;

/**
 * Общий прогноз погоды на день для отображения в списке
 */
public class DayWeather {

    private Date day;
    private float minTemp;
    private float maxTemp;
    private float minWindSpeed;
    private float maxWindSpeed;
    private Collection<String> description;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public float getMinWindSpeed() {
        return minWindSpeed;
    }

    public void setMinWindSpeed(float minWindSpeed) {
        this.minWindSpeed = minWindSpeed;
    }

    public float getMaxWindSpeed() {
        return maxWindSpeed;
    }

    public void setMaxWindSpeed(float maxWindSpeed) {
        this.maxWindSpeed = maxWindSpeed;
    }

    public Collection<String> getDescription() {
        return description;
    }

    public void setDescription(Collection<String> description) {
        this.description = description;
    }
}
