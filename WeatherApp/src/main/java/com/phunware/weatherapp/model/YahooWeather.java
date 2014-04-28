package com.phunware.weatherapp.model;

import java.io.Serializable;

public class YahooWeather implements Serializable
{
    YahooWeatherQuery query;

    public YahooWeatherQuery getQuery () { return query; }
}
