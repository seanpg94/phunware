package com.phunware.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChannelItem implements Serializable
{
    String title;
    String lat;
    @SerializedName("long")
    String _long;
    String link;
    String pubDate;
    ItemCondition condition;
    String description;
    ItemForecast [] forecast;
    ItemGuid guid;

}
