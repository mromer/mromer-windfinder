package com.mromer.windfinder.utils;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

import com.mromer.windfinder.bean.Station;

public class SharedPreferencesUtil {
	
	private static String SHARED_STATIONS_LIST = "stations";
	
	public static String PROPERTY_NOTIFICATION_ACTIVED = "notification_actived";
	public static String SHARED_WIND_LEVEL = "minimun_wind_level";
	public static String SHARED_WIND_DIRECTION = "wind_direction";
	
	public static Map<String, String> getStationsSelected(Context context) {
		
		SharedPreferences prefs = context.getSharedPreferences(
				SHARED_STATIONS_LIST, Context.MODE_PRIVATE);
		
		@SuppressWarnings("unchecked")
		Map<String, String> stationsSelected = (Map<String, String>) prefs.getAll();
		
		return stationsSelected;
		
	}
	
	public static boolean isStationSelected(Context context, String stationId) {
		
		SharedPreferences prefs = context.getSharedPreferences(
				SHARED_STATIONS_LIST, Context.MODE_PRIVATE);
		
		prefs.getBoolean(stationId, false);
		
		return prefs.getBoolean(stationId, false);
		
	}
	
	public static void addStationToSharedPreferences(Context context, Station station) {

		SharedPreferences prefs = context.getSharedPreferences(
				SHARED_STATIONS_LIST, Context.MODE_PRIVATE);		


		prefs.edit().putString(station.getId(), station.getId()).commit();

	}

	public static void removeStationToSharedPreferences(Context context, String stationId) {

		// from list stations preferences
		SharedPreferences prefsListStations = context.getSharedPreferences(
				SHARED_STATIONS_LIST, Context.MODE_PRIVATE);		
		prefsListStations.edit().remove(stationId).commit();
		
		// from station preferences
		SharedPreferences prefsStation = context.getSharedPreferences(
				stationId, Context.MODE_PRIVATE);		
		prefsStation.edit().clear();
	}
	
	
	public static Map<String, ?> getStationPreferences(Context context, String stationId) {
		Map<String, ?> stationData = null;
		
		SharedPreferences prefs = context.getSharedPreferences(
				stationId, Context.MODE_PRIVATE);		

		if (prefs != null) {
			stationData = prefs.getAll();
		}		
		
		return stationData;
	}
	
	
	/**
	 * Return wind direction stored in shared preferences. "" if it is not set
	 * */
	public static String getWindDirectionStation(Context context, String stationId) {
		
		String windDirection = null;

		Map<String, ?> stationPreferences = SharedPreferencesUtil.getStationPreferences(context, stationId);

		if (stationPreferences != null) {
			windDirection = (String) stationPreferences.get(SharedPreferencesUtil.SHARED_WIND_DIRECTION);
		}
		
		if (windDirection == null) {
			return "";
		}

		return windDirection;
	}


	/**
	 * Return wind speed stored in shared preferences. 0 if it is not set
	 * */
	public static Integer getWindLevelStation(Context context, String stationId) {
		
		String windLevelString = null;
		
		Map<String, ?> stationPreferences = SharedPreferencesUtil.getStationPreferences(context, stationId);

		if (stationPreferences != null) {
			windLevelString = (String) stationPreferences.get(SharedPreferencesUtil.SHARED_WIND_LEVEL);
		}
		
		if (windLevelString != null ) {
			return Integer.parseInt((String) stationPreferences.get(SharedPreferencesUtil.SHARED_WIND_LEVEL));
		}

		return 0;
	}
	
	/**
	 * Return if notification is enabled in shared preferences.
	 * */
	public static boolean isNotificacionEnabled(Context context, String idStation) {

		boolean result = false;

		Map<String, ?> stationPreferences = SharedPreferencesUtil.getStationPreferences(context, idStation);

		if (stationPreferences != null &&
				stationPreferences.get(SharedPreferencesUtil.PROPERTY_NOTIFICATION_ACTIVED) != null) {
			result = (Boolean) stationPreferences.get(SharedPreferencesUtil.PROPERTY_NOTIFICATION_ACTIVED);
		}

		return result;
	}

}
