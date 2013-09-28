package com.mromer.windfinder.bean;

import java.io.Serializable;

public class Forecast implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String timestamp;
	
	private ForecastStation stationForecast;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public ForecastStation getStationForecast() {
		return stationForecast;
	}

	public void setStationForecast(ForecastStation stationForecast) {
		this.stationForecast = stationForecast;
	}
	

	
}
