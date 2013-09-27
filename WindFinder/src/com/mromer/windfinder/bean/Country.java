package com.mromer.windfinder.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Country implements Parcelable {

	private String id;
	private String name;
	private List<Region> regionList;

	public Country(String id, String name, List<Region> regionList) {
		this.id = id;
		this.name = name;			
		this.regionList = regionList;
	}
	
	
	private Country(Parcel in) {
		id = in.readString();
		name = in.readString();
		
		regionList = new ArrayList<Region>();
		in.readList(regionList, Region.class.getClassLoader());
					
	}	
	
	
	public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {
		public Country createFromParcel(Parcel in) {
			return new Country(in);
		}

		public Country[] newArray(int size) {
			return new Country[size];
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
		dest.writeList(regionList);
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

	public List<Region> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<Region> regionList) {
		this.regionList = regionList;
	}

	@Override 
	public String toString() {
		StringBuilder result = new StringBuilder();	    

		result.append("{id: " + getId());
		result.append(", name: " + getName());	
		result.append(", regionList: [" + getRegionList() + "]");
		result.append("}");

		return result.toString();
	}	

}
