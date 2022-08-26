package com.example.icecommtest.model.pojo;

import com.google.gson.annotations.SerializedName;

public class Address{

	@SerializedName("zipcode")
	private String zipcode;

	@SerializedName("number")
	private int number;

	@SerializedName("city")
	private String city;

	@SerializedName("street")
	private String street;

	@SerializedName("geolocation")
	private Geolocation geolocation;

	public void setZipcode(String zipcode){
		this.zipcode = zipcode;
	}

	public String getZipcode(){
		return zipcode;
	}

	public void setNumber(int number){
		this.number = number;
	}

	public int getNumber(){
		return number;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getStreet(){
		return street;
	}

	public void setGeolocation(Geolocation geolocation){
		this.geolocation = geolocation;
	}

	public Geolocation getGeolocation(){
		return geolocation;
	}
}