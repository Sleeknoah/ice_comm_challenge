package com.example.icecommtest.model.pojo;

import com.google.gson.annotations.SerializedName;

public class Rating{

	@SerializedName("rate")
	private Double rate;

	@SerializedName("count")
	private Integer count;

	public void setRate(Double rate){
		this.rate = rate;
	}

	public Double getRate(){
		return rate;
	}

	public void setCount(Integer count){
		this.count = count;
	}

	public Integer getCount(){
		return count;
	}
}