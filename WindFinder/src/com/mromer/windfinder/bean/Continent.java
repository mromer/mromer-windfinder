package com.mromer.windfinder.bean;

import java.util.List;

public class Continent {
	
	private String id;
	private String name;
	private List<Country> countryList;

	public Continent(String id, String name, List<Country> countryList) {
		this.id = id;
		this.name = name;		
		this.countryList = countryList;
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