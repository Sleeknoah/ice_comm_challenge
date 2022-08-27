package com.example.icecommtest.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.icecommtest.R;
import com.example.icecommtest.model.request.SignUpRequest;

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

    public void saveUser(SignUpRequest signUpRequest){
        setUpPref("user");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fname", signUpRequest.getName().getFirstname());
        editor.putString("lname", signUpRequest.getName().getLastname());
        editor.putString("email", signUpRequest.getEmail());
        editor.putString("username", signUpRequest.getUsername());
        editor.putString("phone", signUpRequest.getPhone());

        editor.apply();
    }

    public String getSavedToken(){
        setUpPref(mContext.getString(R.string.token));
        return sharedPreferences.getString("access", null);
    }
}
