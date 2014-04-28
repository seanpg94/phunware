package com.phunware.weatherapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phunware.weatherapp.R;
import com.phunware.weatherapp.helpers.PicassoUtil;
import com.phunware.weatherapp.model.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 4/18/2014.
 */
public class CityAdapter extends ArrayAdapter<City>
{
    private Context _context;
    private int _layoutResourceId;
    private ArrayList<City> _cities;

    public CityAdapter(Context context, int resId, List<City> cities)
    {
        super(context, resId, cities);
        _context = context;

        _layoutResourceId = resId;

        _cities = (ArrayList<City>)cities;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
            convertView = inflater.inflate(_layoutResourceId, parent, false);
        }

        City city = getItem(position);

        if (city != null) {

            TextView tvCity = (TextView) convertView.findViewById(R.id.cityLabel);
            tvCity.setText(city.getCity());

            TextView tvZip = (TextView) convertView.findViewById(R.id.zipLabel);
            tvZip.setText(city.getZipCode());

            final ImageView imageView = (ImageView)convertView.findViewById(R.id.weather_icon);

            PicassoUtil.loadWeatherImage(_context,
                    city.getWeatherCode(),
                    R.dimen.weather_forecast_item_icon_width,
                    R.dimen.weather_forecast_item_icon_height,
                    imageView);

        }

        return convertView;
    }

    public void setList(ArrayList<City> newList)
    {
        this._cities = newList;
    }

}
