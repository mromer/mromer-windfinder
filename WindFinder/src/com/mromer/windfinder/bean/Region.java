package com.mromer.windfinder.bean;

import java.util.List;

public class Region {

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
		StringBuilder result = new StringBuilder();	    

		result.append("{id: " + getId());
		result.append(", name: " + getName());	
		result.append(", stationList: " + getStationList());	
		result.append("}");

		return result.toString();
	}	

}
