package com.mromer.windfinder.bean;

import java.util.List;

public class Country implements DataType {

	public static final String TAG_COUNTRY = "country";
	public static final String TAG_COUNTRY_NAME = "name";
	public static final String TAG_COUNTRY_ID = "id";

	private String id;
	private String name;
	private List<Region> regionList;

	public Country(String id, String name, List<Region> regionList) {
		this.id = id;
		this.name = name;			
		this.regionList = regionList;
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
		//due to italic text style
		return getName() + " ";
	}	

}
