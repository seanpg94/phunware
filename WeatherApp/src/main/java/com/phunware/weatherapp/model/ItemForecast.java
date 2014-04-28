package com.phunware.weatherapp.model;

import java.io.Serializable;

public class ItemForecast implements Serializable
{
    String code;
    String date;
    String day;
    String high;
    String low;
    String text;

    public String getDay() { return day; }

    public String getLowTemp() { return low; }
    public String getHighTemp() { return high; }

    public String getWeatherCode() { return code; }
}
