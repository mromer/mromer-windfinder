package com.mromer.windfinder.bean;

import java.io.Serializable;
import java.util.HashMap;

public class ForecastItem implements Serializable{	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String date;
	
	private String time;
	
	private HashMap<String, ForecastData> forecastDataMap;
	
			
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
