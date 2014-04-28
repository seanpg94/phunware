package com.phunware.weatherapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phunware.weatherapp.R;
import com.phunware.weatherapp.helpers.PicassoUtil;
import com.phunware.weatherapp.model.ItemForecast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 4/18/2014.
 */
public class ForecastAdapter extends ArrayAdapter<ItemForecast>
{
    private Context _context;
    private int _layoutResourceId;
    private ArrayList<ItemForecast> _forecasts;

    public ForecastAdapter(Context context, int resId, List<ItemForecast> forecasts)
    {
        super(context, resId, forecasts);
        _context = context;

        _layoutResourceId = resId;

        _forecasts = (ArrayList<ItemForecast>)forecasts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
            convertView = inflater.inflate(_layoutResourceId, parent, false);
        }

        ItemForecast forecast = getItem(position);

        if (forecast != null) {

            final ImageView ivImage = (ImageView)convertView.findViewById(R.id.current_weather_image);

            PicassoUtil.loadWeatherImage(_context,
                    forecast.getWeatherCode(),
                    R.dimen.weather_forecast_item_icon_width,
                    R.dimen.weather_forecast_item_icon_height,
                    ivImage);

            TextView tvDay = (TextView) convertView.findViewById(R.id.day_label);
            tvDay.setText(forecast.getDay());

            TextView tvLoTemp = (TextView) convertView.findViewById(R.id.low_temp_label);
            tvLoTemp.setText(forecast.getLowTemp());

            TextView tvHiTemp = (TextView) convertView.findViewById(R.id.hi_temp_label);
            tvHiTemp.setText(forecast.getHighTemp());

            if (position == getCount()-1)
            {
                convertView.setBackgroundColor(Color.WHITE);
            }
            else
            {
                convertView.setBackgroundResource(R.drawable.bg_day);
            }
        }

        return convertView;
    }
}
