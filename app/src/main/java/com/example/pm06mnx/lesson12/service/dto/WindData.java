package com.example.pm06mnx.lesson12.service.dto;

/**
 * Основные данные по погоде
 */
public class WindData {

    private Float temp;
    private Float pressure;
    private Integer humidity;

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }
}
