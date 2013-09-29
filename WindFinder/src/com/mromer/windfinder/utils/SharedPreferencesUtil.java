package com.mromer.windfinder.utils;

import java.util.Map;

import com.mromer.windfinder.bean.Station;

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
	
	public static void addStationToSharedPreferences(Context context, Station station) {

		SharedPreferences prefs = context.getSharedPreferences(
				"stations", Context.MODE_PRIVATE);		


		prefs.edit().putString(station.getId(), station.getId()).commit();

	}

	public static void removeStationToSharedPreferences(Context context, String stationId) {

		SharedPreferences prefs = context.getSharedPreferences(
				"stations", Context.MODE_PRIVATE);

		prefs.edit().remove(stationId).commit();
	}

}
