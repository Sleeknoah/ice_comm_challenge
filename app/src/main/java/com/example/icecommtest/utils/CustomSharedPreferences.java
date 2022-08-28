package com.example.icecommtest.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.icecommtest.R;
import com.example.icecommtest.model.pojo.Address;
import com.example.icecommtest.model.pojo.Geolocation;
import com.example.icecommtest.model.pojo.Name;
import com.example.icecommtest.model.request.SignUpRequest;

import java.util.Objects;

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
        setUpPref(mContext.getString(R.string.user_details));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fname", signUpRequest.getName().getFirstname());
        editor.putString("lname", signUpRequest.getName().getLastname());
        editor.putString("email", signUpRequest.getEmail());
        editor.putString("username", signUpRequest.getUsername());
        editor.putString("phone", signUpRequest.getPhone());

        editor.apply();
    }

    public SignUpRequest getUsers(){
        setUpPref(mContext.getString(R.string.user_details));
        SignUpRequest signUpRequest =  new SignUpRequest();
        Name name = new Name();
        name.setFirstname(sharedPreferences.getString("fname", null));
        name.setLastname(sharedPreferences.getString("lname", null));
        signUpRequest.setName(name);
        signUpRequest.setUsername(sharedPreferences.getString("username", null));
        signUpRequest.setEmail(sharedPreferences.getString("email", null));
        signUpRequest.setPhone(sharedPreferences.getString("phone", null));

        return signUpRequest;
    }

    public String getSavedToken(){
        setUpPref(mContext.getString(R.string.token));
        return sharedPreferences.getString("access", null);
    }
}
