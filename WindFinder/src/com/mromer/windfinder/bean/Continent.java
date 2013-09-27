package com.mromer.windfinder.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Continent implements Parcelable {
	
	private String id;
	private String name;
	private List<Country> countryList;

	public Continent(String id, String name, List<Country> countryList) {
		this.id = id;
		this.name = name;		
		this.countryList = countryList;
	}
	
	private Continent(Parcel in) {
		id = in.readString();
		name = in.readString();
		
		countryList = new ArrayList<Country>();
		in.readList(countryList, Country.class.getClassLoader());
					
	}	
	
	
	public static final Parcelable.Creator<Continent> CREATOR = new Parcelable.Creator<Continent>() {
		public Continent createFromParcel(Parcel in) {
			return new Continent(in);
		}

		public Continent[] newArray(int size) {
			return new Continent[size];
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
		dest.writeList(countryList);
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

	public List<Country> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<Country> countryList) {
		this.countryList = countryList;
	}	
	

	@Override 
	public String toString() {
		StringBuilder result = new StringBuilder();	    

		result.append("{id: " + getId());
		result.append(", name: " + getName());
		result.append(", countryList: [" + getCountryList() + "]");	   
		result.append("}");

		return result.toString();
	}	
	
	
}