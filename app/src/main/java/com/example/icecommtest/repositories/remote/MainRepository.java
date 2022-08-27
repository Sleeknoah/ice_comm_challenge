package com.example.icecommtest.repositories.remote;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.icecommtest.model.response.ProductResponse;
import com.example.icecommtest.utils.ApiHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    public static MutableLiveData<List<ProductResponse>> productResponseMutableLiveData = new MutableLiveData<>();
    public static MutableLiveData<String> messageMutableLiveData = new MutableLiveData<>();
    public static MutableLiveData<List<String>> categoryResponseMutableLiveData = new MutableLiveData<>();

    public void productByCategory(String category){
        ApiHelper.authInterface().productCategory(category)
                .enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductResponse>> call,
                                   @NonNull Response<List<ProductResponse>> response) {
                if (response.isSuccessful()){
                    productResponseMutableLiveData.postValue(response.body());
                }else {
                    messageMutableLiveData.postValue("error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductResponse>> call,
                                  @NonNull Throwable t) {
                messageMutableLiveData.postValue("error");
            }
        });
    }

    //Get list of categories
    public void categories(){
        ApiHelper.authInterface().category().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call,
                                   @NonNull Response<List<String>> response) {
                if (response.isSuccessful()){
                    categoryResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call,
                                  @NonNull Throwable t) {

            }
        });
    }
}
