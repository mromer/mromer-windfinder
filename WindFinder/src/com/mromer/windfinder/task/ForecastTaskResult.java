package com.mromer.windfinder.task;

import java.util.List;

import com.mromer.windfinder.bean.Forecast;

public class ForecastTaskResult {
	
	private boolean error;
	
	private String desc;
	
	private List<Forecast> forecastList;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Forecast> getForecastList() {
		return forecastList;
	}

	public void setForecastList(List<Forecast> forecastList) {
		this.forecastList = forecastList;
	}
	
	

}
