package com.phunware.weatherapp.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Sean on 4/22/2014.
 */
public class ZipcodeManager implements Iterable<String>, IZipcodeManager {

    private Context _context;
    private ArrayList<String> _theList = new ArrayList<String>();

    private static String PREF_NAME = "jsonList";

    public ZipcodeManager(Context context)
    {
        _context = context;
        restoreList();
    }

    public Iterator<String> getIterator()
    {
        return this.iterator();
    }

    public void addZipcode(String zip)
    {
        _theList.add(zip);
        persistList();
    }

    public void removeZipcode(String zip)
    {
        if (_theList.contains(zip)) {
            _theList.remove(zip);
            persistList();
        }
    }

    public Iterator<String> iterator() {
        return _theList.iterator();
    }

    public String getZipList()
    {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("theList", "");
    }

    public void clearAll()
    {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("theList", "");
        edit.commit();
    }

    private void restoreList()
    {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("theList", "");

        _theList = new ArrayList<String>();
        try {
            JSONArray jArray = new JSONArray(json);
            if (jArray != null) {
                for (int i = 0; i < jArray.length(); i++) {
                    _theList.add(jArray.get(i).toString());
                }
            }
        }
        catch (JSONException je)
        {

        }

    }

    private void persistList()
    {
        JSONArray jsonArray = new JSONArray(_theList);
        String json = jsonArray.toString();
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("theList", json);
        edit.commit();
    }

}
