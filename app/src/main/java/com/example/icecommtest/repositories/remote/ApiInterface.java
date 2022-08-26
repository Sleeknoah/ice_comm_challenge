package com.example.icecommtest.repositories.remote;

import com.example.icecommtest.model.request.LoginRequest;
import com.example.icecommtest.model.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    //Login interface set up here
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
