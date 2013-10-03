package com.mromer.windfinder.bean;



public class Station implements DataType {
	
	public static final String TAG_STATION = "station";
	public static final String TAG_STATION_NAME = "name";
	public static final String TAG_STATION_ID = "id";

	private String id;
	private String name;	
	

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
}
