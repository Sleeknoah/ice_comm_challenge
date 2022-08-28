package com.example.icecommtest.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.icecommtest.repositories.local.entity.Cart;
import com.example.icecommtest.repositories.local.repository.CartRepository;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private CartRepository mRepository;

    private final LiveData<List<Cart>> mAllCart;

    public CartViewModel (Application application) {
        super(application);
        mRepository = new CartRepository(application);
        mAllCart = mRepository.mAllCart();
    }

    public LiveData<List<Cart>> getAllCart() { return mAllCart; }

    public void insert(Cart cart) { mRepository.
            insert(cart); }
}
