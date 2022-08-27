package com.example.icecommtest.utils;

import com.example.icecommtest.repositories.remote.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

    public final class ApiHelper {
        public ApiHelper() {
        }

        public static ApiInterface authInterface(){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl.FAKE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit.create(ApiInterface.class);
        }
}
