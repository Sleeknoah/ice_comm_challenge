package com.example.icecommtest.model.response;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse{

	@SerializedName("id")
	private int id;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}