package com.example.icecommtest.model.pojo;

import com.google.gson.annotations.SerializedName;

public class Geolocation{

	@SerializedName("long")
	private String lng;

	@SerializedName("lat")
	private String lat;

	public void setLng(String lng){
		this.lng = lng;
	}

	public String getLng(){
		return lng;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}
}