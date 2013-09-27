package com.mromer.windfinder.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Region implements Parcelable{

	private String id;
	private String name;
	private List<Station> stationList;


	public Region(String id, String name, List<Station> stationList) {
		this.id = id;
		this.name = name;
		this.stationList = stationList;
	}
	
	
	
	private Region(Parcel in) {
		id = in.readString();
		name = in.readString();
		
		stationList = new ArrayList<Station>();
		in.readList(stationList, Station.class.getClassLoader());
					
	}	
	
	
	public static final Parcelable.Creator<Region> CREATOR = new Parcelable.Creator<Region>() {
		public Region createFromParcel(Parcel in) {
			return new Region(in);
		}

		public Region[] newArray(int size) {
			return new Region[size];
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
		dest.writeList(stationList);
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

	public List<Station> getStationList() {
		return stationList;
	}

	public void setStationList(List<Station> stationList) {
		this.stationList = stationList;
	}

	@Override 
	public String toString() {
		StringBuilder result = new StringBuilder();	    

		result.append("{id: " + getId());
		result.append(", name: " + getName());	
		result.append(", stationList: " + getStationList());	
		result.append("}");

		return result.toString();
	}	

}
