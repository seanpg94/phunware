package com.phunware.weatherapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phunware.weatherapp.adapters.CityAdapter;
import com.phunware.weatherapp.fragments.AddZipFragment;
import com.phunware.weatherapp.fragments.CityListFragment;
import com.phunware.weatherapp.helpers.IZipcodeManager;
import com.phunware.weatherapp.helpers.ZipcodeManager;
import com.phunware.weatherapp.model.City;
import com.phunware.weatherapp.model.CityList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class MainActivity extends ActionBarActivity implements AddZipFragment.AddFragmentListener
{
    private RequestQueue _queue;
    private AddZipFragment _addZipFragment;
    private CityListFragment _cityListFragment;
    private CityAdapter _adapter;
    private ArrayList<City> _cities;
    private IZipcodeManager _zipManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // request progress indicator on the action bar
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
        {
            _cities = (ArrayList<City>)savedInstanceState.getSerializable("cities");
        }
        else
        {
            _cities = new ArrayList<City>();
        }

        _queue = Volley.newRequestQueue(this);
        _zipManager = new ZipcodeManager(this);

        Iterator<String> iterator = _zipManager.getIterator();
        if (iterator.hasNext() == false)
        {
            // first time,  intialize to 3 zip codes
            _zipManager.addZipcode("78613");
            _zipManager.addZipcode("10001");
            _zipManager.addZipcode("90001");
        }

        _addZipFragment = (AddZipFragment)getSupportFragmentManager().findFragmentById(R.id.add_zip_fragment);
        _cityListFragment = (CityListFragment)getSupportFragmentManager().findFragmentById(R.id.city_list_fragment);

        _adapter = new CityAdapter(this, R.layout.item_view, _cities);
        _cityListFragment.setListAdapter(_adapter);

        hideAddFragment();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (_cities.isEmpty())
        {
            addCities();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putSerializable("cities", _cities);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.add_zip_code) {
            showAddFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // AddZipFragment.AddFragmentListener
    public void onAddZipCode(String zip)
    {
        addCity(zip);
        hideAddFragment();
    }

    // AddZipFragment.AddFragmentListener
    public void onAddCancel()
    {
        hideAddFragment();
    }

    public void RefreshCity(int idx, final RefeshCityListener listener)
    {
        // close the add fragment in case it is showing
        hideAddFragment();

        String url;

        final String zipcode = _cities.get(idx).getZipCode();
        final int index = idx;

        try
        {
            url = getString(R.string.weather_zip_url, zipcode);
        }
        catch (Exception e)
        {
            // bad zip code string
            return;
        }

        showProgress(true);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                final City city = getCityFromJson(jsonObject);
                if (city != null) {
                    _cities.set(index, city);
                    updateAdapter();
                    listener.onRefreshComplete(city);
                }
                showProgress(false);
            }
        }, _genericErrorHandler);

        _queue.add(jsonObjectRequest);
    }

    private void addCities()
    {
        String zipList = _zipManager.getZipList();
        if (zipList.isEmpty())
        {
            return;
        }

        JSONObject obj = new JSONObject();

        try {
            JSONArray zipArray = new JSONArray(zipList);
            obj.put("zipList", zipArray);
        }
        catch (JSONException je)
        {
            return;
        }

        String url = getString(R.string.zipcode_list_url);

        showProgress(true);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                final CityList cities = gson.fromJson(jsonObject.toString(), CityList.class);
                if (cities != null) {
                    _cities = new ArrayList<City>(Arrays.asList(cities.getCities()));
                    updateAdapter();
                }
                showProgress(false);
            }
        }, _genericErrorHandler);

        _queue.add(jsonObjectRequest);
    }

    private void addCity(String zipcode)
    {
        String url;

        final String zip = zipcode;

        try
        {
            url = getString(R.string.weather_zip_url, zipcode);
        }
        catch (Exception e)
        {
            // bad zip code string
            return;
        }

        showProgress(true);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                final City city = getCityFromJson(jsonObject);
                if (city != null) {
                    if (city.isValid()) {
                        _zipManager.addZipcode(zip);
                        _cities.add(city);
                        updateAdapter();

                        // select the item at the end of the list
                        // We add new zip codes at the end of the data structure,
                        // so this is a safe operation.
                        ListView listView = _cityListFragment.getListView();
                        if (_adapter.getCount() != 0) {
                            int pos = _adapter.getCount() -1;
                            View newView = _adapter.getView(pos, null, null);
                            listView.performItemClick(newView, pos, pos);
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Invalid zip code", Toast.LENGTH_LONG).show();
                    }
                }
                showProgress(false);
            }
        }, _genericErrorHandler);

        _queue.add(jsonObjectRequest);
    }


    private City getCityFromJson(JSONObject jsonObject)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(jsonObject.toString(), City.class);
    }

    private void updateAdapter()
    {
        _adapter.clear();

        for (City city : _cities) {
            _adapter.add(city);
        }

        _adapter.notifyDataSetChanged();
        _cityListFragment.getListView().invalidateViews();
    }

    private void showAddFragment()
    {
        getSupportFragmentManager()
                .beginTransaction()
                .show(_addZipFragment)
                .commit();
    }

    private void hideAddFragment()
    {
        getSupportFragmentManager()
                .beginTransaction()
                .hide(_addZipFragment)
                .commit();
    }

    private void showProgress(boolean visible)
    {
        this.setProgressBarIndeterminateVisibility(visible);
    }

    private Response.ErrorListener _genericErrorHandler = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(MainActivity.this, "error getting zip code for city", Toast.LENGTH_LONG).show();
            MainActivity.this.setProgressBarIndeterminateVisibility(false);
        }
    };

    public interface RefeshCityListener
    {
        void onRefreshComplete(City city);
    }
}
