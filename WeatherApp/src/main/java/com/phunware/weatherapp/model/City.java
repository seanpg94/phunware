package com.phunware.weatherapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sean on 4/18/2014.
 */
public class City implements Serializable {
    String zipcode;
    YahooWeather weather;

    public City(String zip, YahooWeather weather)
    {
        this.zipcode = zip;
        this.weather = weather;
    }

    public String getCity()
    {
        if (weather != null)

            return weather.getQuery().getResults().getChannel().getCity();
        else
            return "";
    }

    public String getZipCode()
    {
        return zipcode;
    }

    public String getCurrentTemp()
    {
        return weather.getQuery().getResults().getChannel().item.condition.temp;
    }

    public ArrayList<ItemForecast> getFiveDayForecast()
    {
        return new ArrayList<ItemForecast>(Arrays.asList(weather.getQuery().getResults().getChannel().item.forecast));
    }

    public String getWeatherCode()
    {
        return weather.getQuery().getResults().getChannel().item.condition.code;
    }

    public boolean isValid()
    {
        String title = weather.getQuery().getResults().getChannel().item.title;

        if (title.equalsIgnoreCase("city not found"))
            return false;
        else
            return true;
    }

}
