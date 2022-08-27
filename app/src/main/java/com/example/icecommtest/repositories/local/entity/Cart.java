package com.example.icecommtest.repositories.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_table")
public class Cart {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title")
    private String mTitle;

    @NonNull
    @ColumnInfo(name = "price")
    private String mPrice;

    @NonNull
    @ColumnInfo(name = "image")
    private String mImage;

    @NonNull
    @ColumnInfo(name = "quantity")
    private Integer mQuantity;

    public Cart(@NonNull String mTitle, @NonNull String mPrice, @NonNull String mImage, @NonNull Integer mQuantity) {
        this.mTitle = mTitle;
        this.mPrice = mPrice;
        this.mImage = mImage;
        this.mQuantity = mQuantity;
    }


    public String getTitle() {
        return mTitle;
    }


    public String getPrice() {
        return mPrice;
    }


    public String getImage() {
        return mImage;
    }


    public Integer getQuantity() {
        return mQuantity;
    }
}
