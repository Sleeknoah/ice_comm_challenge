package com.example.icecommtest.repositories.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.icecommtest.repositories.local.dao.CartDao;
import com.example.icecommtest.repositories.local.entity.Cart;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Cart.class}, version = 1, exportSchema = false)
public abstract class CacheDatabase extends RoomDatabase {

    public abstract CartDao cartDao();
    private static volatile CacheDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    //Defined a singleton, CacheDatabase, to prevent having multiple instances of the database opened at the same time.
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CacheDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CacheDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CacheDatabase.class, "cache_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
