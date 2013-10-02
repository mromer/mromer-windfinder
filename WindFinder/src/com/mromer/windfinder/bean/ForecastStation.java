package com.mromer.windfinder.bean;

import java.io.Serializable;
import java.util.List;

public class ForecastStation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String TAG_ID = "id";
	public static final String TAG_NAME = "name";
	public static final String TAG_TIMEZONE = "timezone";
	public static final String TAG_FORECAST = "forecast";
	

	private String id;
	
	private String name;
	
	private String timezone;	
	
	private List<ForecastItem> forecastItems;

	public List<ForecastItem> getForecastItems() {
		return forecastItems;
	}

	public void setForecastItems(List<ForecastItem> forecastItems) {
		this.forecastItems = forecastItems;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	

}
