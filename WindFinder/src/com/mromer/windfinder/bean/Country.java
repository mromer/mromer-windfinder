package com.mromer.windfinder.bean;

import java.util.List;

public class Country {

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
		StringBuilder result = new StringBuilder();	    

		result.append("{id: " + getId());
		result.append(", name: " + getName());	
		result.append(", regionList: [" + getRegionList() + "]");
		result.append("}");

		return result.toString();
	}	

}
