package com.phunware.weatherapp.helpers;

import java.util.Iterator;

/**
 * Created by Sean on 4/22/2014.
 */
public interface IZipcodeManager {
    void addZipcode(String zip);
    void removeZipcode(String zip);
    Iterator<String> getIterator();
    String getZipList();
    void clearAll();
}
