package com.mromer.windfinder.bean;

import android.os.Parcel;
import android.os.Parcelable;


public class Station implements Parcelable {

	private String id;
	private String name;
	private String keyword;
	private String superforecast;
	private String statistic;
	private String report;
	private String forecast;
	private String wavereport;
	private String waveforecast;


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



	public String getKeyword() {
		return keyword;
	}



	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}



	public String getSuperforecast() {
		return superforecast;
	}



	public void setSuperforecast(String superforecast) {
		this.superforecast = superforecast;
	}



	public String getStatistic() {
		return statistic;
	}



	public void setStatistic(String statistic) {
		this.statistic = statistic;
	}



	public String getReport() {
		return report;
	}



	public void setReport(String report) {
		this.report = report;
	}



	public String getForecast() {
		return forecast;
	}



	public void setForecast(String forecast) {
		this.forecast = forecast;
	}



	public String getWavereport() {
		return wavereport;
	}



	public void setWavereport(String wavereport) {
		this.wavereport = wavereport;
	}



	public String getWaveforecast() {
		return waveforecast;
	}



	public void setWaveforecast(String waveforecast) {
		this.waveforecast = waveforecast;
	}



	@Override 
	public String toString() {
		StringBuilder result = new StringBuilder();	    

		result.append("{id: " + getId());
		result.append(", name: " + getName());	
		result.append(", keyword: " + getKeyword());
		result.append(", superforecast: " + getSuperforecast());	
		result.append(", statistic: " + getStatistic());	
		result.append(", report: " + getReport());	
		result.append(", forecast: " + getForecast());	
		result.append(", wavereport: " + getWavereport());	
		result.append(", waveforecast: " + getWaveforecast());			
		result.append("}");

		return result.toString();
	}



	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}	

}
