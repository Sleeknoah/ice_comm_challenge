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

    public Cart(@NonNull String mTitle, @NonNull String mPrice, @NonNull String mImage, int mQuantity) {
        this.mTitle = mTitle;
        this.mPrice = mPrice;
        this.mImage = mImage;
        this.mQuantity = mQuantity;
    }

    @NonNull
    public String getmTitle() {
        return mTitle;
    }

    @NonNull
    public String getmPrice() {
        return mPrice;
    }

    @NonNull
    public String getmImage() {
        return mImage;
    }

    public int getmQuantity() {
        return mQuantity;
    }
}
