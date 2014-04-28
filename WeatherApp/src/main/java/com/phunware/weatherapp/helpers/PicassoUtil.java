package com.phunware.weatherapp.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.phunware.weatherapp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Sean on 4/25/2014.
 */
public class PicassoUtil {

    public static String WEATHER_URL_FMT = "http://l.yimg.com/a/i/us/we/52/%s.gif";

    public static void loadWeatherImage(Context context, String weatherCode, int width_dimen, int height_dimen, ImageView imageView)
    {
        String url = String.format(WEATHER_URL_FMT, weatherCode);

        try
        {
            Picasso.with(context)
                    .load(url)
                    .resizeDimen(width_dimen, height_dimen)
                    .placeholder(R.drawable.no_image_placeholder)
                    .into(imageView);
        }
        catch (Exception e) {
            Log.v("DEBUG", "picasso exception : " + e.toString());
        }

    }

}
