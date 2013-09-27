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
	
	
	private Station(Parcel in) {
		id = in.readString();
		name = in.readString();
		keyword = in.readString();
		superforecast = in.readString();
		statistic = in.readString();
		report = in.readString();
		forecast = in.readString();
		wavereport = in.readString();
		waveforecast = in.readString();		
	}
	
	public Station() {
		
	}
	
	public static final Parcelable.Creator<Station> CREATOR = new Parcelable.Creator<Station>() {
		public Station createFromParcel(Parcel in) {
			return new Station(in);
		}

		public Station[] newArray(int size) {
			return new Station[size];
		}
	};


	@Override
	public int describeContents() {
		
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(keyword);
		dest.writeString(superforecast);
		dest.writeString(statistic);
		dest.writeString(report);
		dest.writeString(forecast);
		dest.writeString(wavereport);
		dest.writeString(waveforecast);		
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


}
