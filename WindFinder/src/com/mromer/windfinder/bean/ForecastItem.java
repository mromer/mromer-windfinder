package com.mromer.windfinder.bean;

import java.io.Serializable;
import java.util.HashMap;

public class ForecastItem implements Serializable{	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String WIND_SPEED 		= "wind_speed";
	public static final String WIND_DIRECTION 	= "wind_direction";
	
	private String date;
	
	private String time;
	
	/**
	 * To add forecast data: WIND_SPEED, WIND_DIRECTION...
	 * */
	private HashMap<String, ForecastData> forecastDataMap;
	
	
	/**
	 * Return wind speed or 0 if it is not set.
	 * */
	public Integer getWindSpeed() {

		String windSpeedString = getForecastDataMap().get(ForecastItem.WIND_SPEED).getValue();

		if (windSpeedString == null || windSpeedString.trim().length() == 0) {
			return 0;
		}

		Integer windLevelInteger = Integer.parseInt(windSpeedString);

		return windLevelInteger;
	}
	
	
	/**
	 * Return wind direction or "" if it is not set
	 * */
	public String getWindDirection() {

		String windDirection = getForecastDataMap().get(ForecastItem.WIND_DIRECTION).getValue();
		
		if (windDirection == null) {
			return "";
		}

		return windDirection;
	}
	
			
	public HashMap<String, ForecastData> getForecastDataMap() {
		return forecastDataMap;
	}
	public void setForecastDataMap(HashMap<String, ForecastData> forecastDataMap) {
		this.forecastDataMap = forecastDataMap;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}		

}
