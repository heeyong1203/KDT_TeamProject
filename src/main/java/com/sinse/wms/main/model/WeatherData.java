package com.sinse.wms.main.model;

public class WeatherData {
    public double temperature;
    public long sunrise;
    public long sunset;
    public String icon;

    public WeatherData(double temp, long rise, long set, String iconCode) {
        this.temperature = temp;
        this.sunrise = rise;
        this.sunset = set;
        this.icon = iconCode;
    }
}
