package com.phunware.weatherapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phunware.weatherapp.R;
import com.phunware.weatherapp.adapters.ForecastAdapter;
import com.phunware.weatherapp.helpers.PicassoUtil;
import com.phunware.weatherapp.model.City;
import com.phunware.weatherapp.model.ItemForecast;
import com.phunware.weatherapp.widget.CenterLockHorizontalScrollView;

import java.util.ArrayList;

public class CityDetailsFragment extends Fragment
{
    private City _city;

    /*
    *   Note:
    *
    *     Use url : http://l.yimg.com/a/i/us/we/52/xx.gif
    *
    *     where xx is the 'code' from the each day's forecast
    *
    *
     */

    public static CityDetailsFragment newInstance(City city)
    {
        CityDetailsFragment f = new CityDetailsFragment();
        Bundle bundle = new Bundle();
        f._city = city;
        bundle.putSerializable("city", city);
        f.setArguments(bundle);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.city_detail, container, false);

        final TextView cityLabel = (TextView)view.findViewById(R.id.city_label);
        cityLabel.setText(_city.getCity());

        final TextView tempLabel = (TextView)view.findViewById(R.id.temp_label);
        tempLabel.setText(_city.getCurrentTemp() + "°");

        final ImageView image = (ImageView)view.findViewById(R.id.current_weather_image);

        PicassoUtil.loadWeatherImage(getActivity(),
                _city.getWeatherCode(),
                R.dimen.weather_day_icon_width,
                R.dimen.weather_day_icon_height,
                image);

        CenterLockHorizontalScrollView centerLockHorizontalScrollview = (CenterLockHorizontalScrollView) view.findViewById(R.id.scrollView);
        ArrayList<ItemForecast> fiveDayList = _city.getFiveDayForecast();
        ForecastAdapter _adapter = new ForecastAdapter(getActivity(), R.layout.forecast_day, fiveDayList);
        centerLockHorizontalScrollview.setAdapter(getActivity(), _adapter);

        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null)
        {
            _city = (City)savedInstanceState.getSerializable("city");
        }
    }
}
