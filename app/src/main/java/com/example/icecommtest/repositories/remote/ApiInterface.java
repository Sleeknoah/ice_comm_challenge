package com.example.icecommtest.repositories.remote;

import com.example.icecommtest.model.request.CartRequest;
import com.example.icecommtest.model.request.LoginRequest;
import com.example.icecommtest.model.request.SignUpRequest;
import com.example.icecommtest.model.response.CartResponse;
import com.example.icecommtest.model.response.LoginResponse;
import com.example.icecommtest.model.response.ProductResponse;
import com.example.icecommtest.model.response.SignUpResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    //Login interface set up here
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    //SignUp interface set up here
    @POST("users")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);

    //Search product by Category
    @GET("products/category/{category}")
    Call<List<ProductResponse>> productCategory(@Path("category") String category);

    //Get category list
    @GET("products/categories")
    Call<List<String>> category();

    //Add to cart
    @POST("carts")
    Call<CartResponse> addCart(@Body CartRequest cartRequest);
}
