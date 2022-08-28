package com.example.icecommtest.model.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequest{

	@SerializedName("password")
	private String password;

	@SerializedName("username")
	private String username;

	public String getPassword(){
		return password;
	}

	public String getUsername(){
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}