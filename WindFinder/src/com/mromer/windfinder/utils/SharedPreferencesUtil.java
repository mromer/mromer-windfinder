package com.mromer.windfinder.utils;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
	
	
	public static Map<String, String> getStationsSelected(Context context) {
		
		SharedPreferences prefs = context.getSharedPreferences(
			      "stations", Context.MODE_PRIVATE);
		
		@SuppressWarnings("unchecked")
		Map<String, String> stationsSelected = (Map<String, String>) prefs.getAll();
		
		return stationsSelected;
		
	}
	
	public static boolean isStationSelected(Context context, String stationId) {
		
		SharedPreferences prefs = context.getSharedPreferences(
			      "stations", Context.MODE_PRIVATE);
		
		prefs.getBoolean(stationId, false);
		
		return prefs.getBoolean(stationId, false);
		
	}

}
