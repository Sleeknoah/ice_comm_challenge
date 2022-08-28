package com.example.icecommtest.model.request;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CartRequest{

	@SerializedName("date")
	private String date;

	@SerializedName("userId")
	private Integer userId;

	@SerializedName("products")
	private List<ProductsItem> products;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setProducts(List<ProductsItem> products){
		this.products = products;
	}

	public List<ProductsItem> getProducts(){
		return products;
	}
}