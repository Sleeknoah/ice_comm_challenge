package com.example.icecommtest.model.request;

import com.google.gson.annotations.SerializedName;

public class ProductsItem{

	@SerializedName("quantity")
	private Integer quantity;

	@SerializedName("productId")
	private Integer productId;

	public void setQuantity(Integer quantity){
		this.quantity = quantity;
	}

	public Integer getQuantity(){
		return quantity;
	}

	public void setProductId(Integer productId){
		this.productId = productId;
	}

	public Integer getProductId(){
		return productId;
	}
}