package com.example.icecommtest.repositories.remote;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.icecommtest.model.request.LoginRequest;
import com.example.icecommtest.model.response.LoginResponse;
import com.example.icecommtest.utils.BaseUrl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthRepository {
    ApiInterface  apiInterface;
    public static MutableLiveData<LoginResponse> loginResponseMutableLiveData = new MutableLiveData<>();

    LoginResponse loginResponse = new LoginResponse();

    //Start Api Interface and connect to base url
    ApiInterface authInterface(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.FAKE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);

        return apiInterface;
    }

    //Make login request call  and get response from server
    //And if successful update livedata accordingly
    public void loginUser(LoginRequest loginRequest){
        //Attach Api interface and make call
        authInterface().login(loginRequest)
                .enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call,
                                   @NonNull Response<LoginResponse> response) {
                //Check if response is successful
                if (response.isSuccessful()){
                    loginResponseMutableLiveData.postValue(response.body());
                }else{
                    //Because we do not know the nature of error
                    // let us insert our own error to be safe
                   loginResponse.setToken("Error");
                   loginResponseMutableLiveData.postValue(loginResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                loginResponse.setToken("Error");
                loginResponseMutableLiveData.postValue(loginResponse);
            }
        });
    }

}
