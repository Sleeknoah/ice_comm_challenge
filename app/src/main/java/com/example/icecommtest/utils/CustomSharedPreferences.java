package com.example.icecommtest.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.icecommtest.R;

public class CustomSharedPreferences {
    SharedPreferences sharedPreferences;
    Context mContext;

    public CustomSharedPreferences(Context mContext) {
        this.mContext = mContext;
    }
    private void setUpPref(String name){
        sharedPreferences= mContext.getSharedPreferences(
                name, Context.MODE_PRIVATE);
    }

    public void clearPref(String name){
        setUpPref(name);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void saveToken(String token){
        setUpPref(mContext.getString(R.string.token));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("access", token);
        editor.apply();
    }

    public String getSavedToken(){
        setUpPref(mContext.getString(R.string.token));
        return sharedPreferences.getString("access", null);
    }
}
