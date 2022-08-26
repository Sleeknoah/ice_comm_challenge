package com.example.icecommtest.repositories.remote;

import com.example.icecommtest.model.request.LoginRequest;
import com.example.icecommtest.model.request.SignUpRequest;
import com.example.icecommtest.model.response.LoginResponse;
import com.example.icecommtest.model.response.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    //Login interface set up here
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    //SignUp interface set up here
    @POST("users")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);
}
