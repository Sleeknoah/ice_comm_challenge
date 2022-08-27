package com.example.icecommtest.repositories.local.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.icecommtest.repositories.local.dao.CartDao;
import com.example.icecommtest.repositories.local.database.CacheDatabase;
import com.example.icecommtest.repositories.local.entity.Cart;

import java.util.List;

public class CartRepository {

    private CartDao mCartDao;
    private LiveData<List<Cart>> mAllCart;
    Boolean mContainsPrimaryKey;

    public CartRepository(Application application) {
        CacheDatabase db = CacheDatabase.getDatabase(application);
        mCartDao = db.cartDao();
        mAllCart = mCartDao.getCartList();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Cart>> mAllCart() {
        return mAllCart;
    }

    public Boolean containsPrimaryKey(String title){
        CacheDatabase.databaseWriteExecutor.execute(() -> {
            mContainsPrimaryKey = mCartDao.containsPrimaryKey(title);
        });
       return mContainsPrimaryKey;
    }

    // This method must be called on a non-UI thread or the app will throw an exception.
    public void insert(Cart cart) {
        CacheDatabase.databaseWriteExecutor.execute(() -> {
            mCartDao.insertCart(cart);
        });
    }
}
