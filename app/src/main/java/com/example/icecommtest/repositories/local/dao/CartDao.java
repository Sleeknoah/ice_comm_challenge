package com.example.icecommtest.repositories.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.icecommtest.repositories.local.entity.Cart;

import java.util.List;

@Dao
public interface CartDao {

    @Insert
    void insertCart(Cart cart);

    @Query("SELECT * FROM cart_table")
    LiveData<List<Cart>> getCartList();
}
