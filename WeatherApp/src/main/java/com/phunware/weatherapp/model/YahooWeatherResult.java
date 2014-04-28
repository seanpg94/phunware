package com.phunware.weatherapp.model;

import java.io.Serializable;

public class YahooWeatherResult implements Serializable
{
    YahooWeatherChannel channel;

    public YahooWeatherChannel getChannel() { return channel; }
}
