package com.example.icecommtest.repositories.remote;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.icecommtest.model.response.LoginResponse;
import com.example.icecommtest.model.response.ProductResponse;
import com.example.icecommtest.model.response.SignUpResponse;
import com.example.icecommtest.utils.ApiHelper;
import com.example.icecommtest.utils.BaseUrl;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainRepository {
    public static MutableLiveData<List<ProductResponse>> productResponseMutableLiveData = new MutableLiveData<>();
    public static  MutableLiveData<String> messageMutableLiveData = new MutableLiveData<>();

    ProductResponse productResponse = new ProductResponse();
    private ApiInterface apiInterface;

    ApiInterface authInterface(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.FAKE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);

        return apiInterface;
    }

    public void productByCategory(String category){
        authInterface().productCategory(category).enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductResponse>> call, @NonNull Response<List<ProductResponse>> response) {
                if (response.isSuccessful()){
                    messageMutableLiveData.postValue("success");
                    productResponseMutableLiveData.postValue(response.body());
                }else {
                    messageMutableLiveData.postValue("error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductResponse>> call, @NonNull Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }
}
