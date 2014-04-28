package com.phunware.weatherapp.fragments;


import android.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.phunware.weatherapp.MainActivity;
import com.phunware.weatherapp.R;
import com.phunware.weatherapp.model.City;

public class CityListFragment extends ListFragment implements MainActivity.RefeshCityListener {

    @Override
    public void onListItemClick (ListView l, View v, int position, long id)
    {
        ((MainActivity)getActivity()).RefreshCity(position, this);
    }

    public void onRefreshComplete(City city)
    {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment_container, CityDetailsFragment.newInstance(city))
                .addToBackStack(null)
                .commit();
    }
}
