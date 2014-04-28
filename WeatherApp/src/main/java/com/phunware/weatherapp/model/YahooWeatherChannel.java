package com.phunware.weatherapp.model;

import java.io.Serializable;

public class YahooWeatherChannel implements Serializable
{
    String title;
    String link;
    String description;
    String language;
    String lastBuildDate;
    String ttl;
    ChannelLocation location;
    ChannelUnits units;
    ChannelWind wind;
    ChannelAtmosphere atmosphere;
    ChannelAstronomy astronomy;
    ChannelImage image;
    ChannelItem item;

    public String getCity()
    {
        if (location != null)
        {
            return location.city + ", " + location.region ;
        }
        else
            return "";
    }
}
