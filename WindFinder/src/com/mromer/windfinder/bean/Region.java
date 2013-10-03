package com.mromer.windfinder.bean;

import java.util.List;

public class Region implements DataType {
	
	public static final String TAG_REGION = "region";
	public static final String TAG_REGION_NAME = "name";
	public static final String TAG_REGION_ID = "id";
	

	private String id;
	private String name;
	private List<Station> stationList;


	public Region(String id, String name, List<Station> stationList) {
		this.id = id;
		this.name = name;
		this.stationList = stationList;
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
		//due to italic text style
		return getName() + " ";
	}

}
