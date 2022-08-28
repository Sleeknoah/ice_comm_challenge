package com.example.icecommtest.model.pojo;

import androidx.annotation.NonNull;

public class Cart {
    private String mCart;

    public Cart(String mCart) {
        this.mCart = mCart;
    }

    public String getWord(){return this.mCart;}
}
