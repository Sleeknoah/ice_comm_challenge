package com.example.icecommtest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.icecommtest.model.request.LoginRequest;
import com.example.icecommtest.model.request.SignUpRequest;
import com.example.icecommtest.model.response.LoginResponse;
import com.example.icecommtest.model.response.SignUpResponse;
import com.example.icecommtest.repositories.remote.AuthRepository;

public class AuthViewModel extends ViewModel {
    private MutableLiveData<LoginResponse> loginResponseMutableLiveData;
    private MutableLiveData<SignUpResponse> signUpResponseMutableLiveData;
    AuthRepository authRepository;

    private void init(){
        authRepository = new AuthRepository();
    }

    //Login user from view model
    public void loginUser(LoginRequest loginRequest){
        init();
        authRepository.loginUser(loginRequest);
    }

    //Create Observable for user login
    public LiveData<LoginResponse> observeUserLogin(){
        if (loginResponseMutableLiveData == null){
            loginResponseMutableLiveData = AuthRepository.loginResponseMutableLiveData;
        }
        return loginResponseMutableLiveData;
    }

    //Register user from view model
    public void registerUser(SignUpRequest signUpRequest){
        init();
        authRepository.registerUser(signUpRequest);
    }

    //Create Observable for user sign up
    public LiveData<SignUpResponse> observeUserSignUp(){
        if (signUpResponseMutableLiveData == null){
            signUpResponseMutableLiveData = AuthRepository.signUpResponseMutableLiveData;
        }
        return signUpResponseMutableLiveData;
    }
}
