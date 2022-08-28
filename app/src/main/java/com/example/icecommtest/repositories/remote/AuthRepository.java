package com.example.icecommtest.repositories.remote;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.icecommtest.model.request.LoginRequest;
import com.example.icecommtest.model.request.SignUpRequest;
import com.example.icecommtest.model.response.LoginResponse;
import com.example.icecommtest.model.response.SignUpResponse;
import com.example.icecommtest.utils.ApiHelper;
import com.example.icecommtest.utils.BaseUrl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthRepository {
    ApiInterface  apiInterface;
    public static MutableLiveData<LoginResponse> loginResponseMutableLiveData = new MutableLiveData<>();
    public static MutableLiveData<SignUpResponse> signUpResponseMutableLiveData = new MutableLiveData<>();
    public static MutableLiveData<SignUpResponse> updateResponseMutableLiveData = new MutableLiveData<>();

    LoginResponse loginResponse = new LoginResponse();
    SignUpResponse signUpResponse = new SignUpResponse();


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

    //Make register request call  and get response from server
    //And if successful update livedata accordingly
    public void registerUser(SignUpRequest signUpRequest){
        //Attach Api interface and make call
        authInterface().signUp(signUpRequest)
                .enqueue(new Callback<SignUpResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<SignUpResponse> call,
                                           @NonNull Response<SignUpResponse> response) {
                        //Check if response is successful
                        if (response.isSuccessful()){
                            signUpResponseMutableLiveData.postValue(response.body());
                            Log.d(AuthRepository.class.getSimpleName(), "onResponse: " + response.body().getId());
                        }else{
                            //Because we do not know the nature of error
                            // let us insert 0 to be safe as id usually start at 1
                            signUpResponse.setId(0);
                            signUpResponseMutableLiveData.postValue(signUpResponse);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SignUpResponse> call, @NonNull Throwable t) {
                        signUpResponse.setId(0);
                        signUpResponseMutableLiveData.postValue(signUpResponse);
                    }
                });
    }

    //Update User call done here
    public void updateUser(int id, SignUpRequest signUpRequest){
        //Attach Api interface and make call
        ApiHelper.authInterface().updateUser(id, signUpRequest)
                .enqueue(new Callback<SignUpResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<SignUpResponse> call,
                                           @NonNull Response<SignUpResponse> response) {
                        //Check if response is successful
                        if (response.isSuccessful()){
                            updateResponseMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SignUpResponse> call, @NonNull Throwable t) {

                    }
                });
    }
}
