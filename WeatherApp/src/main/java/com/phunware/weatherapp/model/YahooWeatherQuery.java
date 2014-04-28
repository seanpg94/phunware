package com.phunware.weatherapp.model;

import java.io.Serializable;

public class YahooWeatherQuery implements Serializable
{
    String count;
    String created;
    String lang;
    YahooWeatherResult results;

    public YahooWeatherResult getResults() { return results; }
}
