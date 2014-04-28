package com.phunware.weatherapp.model;

import java.io.Serializable;

/**
 * Created by Sean on 4/18/2014.
 */
public class CityList implements Serializable
{
    City [] cities;

    public CityList()
    {
    }

    public City [] getCities()
    {
        return cities;
    }
}
