package com.example.icecommtest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.icecommtest.model.request.LoginRequest;
import com.example.icecommtest.model.request.SignUpRequest;
import com.example.icecommtest.model.response.LoginResponse;
import com.example.icecommtest.model.response.ProductResponse;
import com.example.icecommtest.model.response.SignUpResponse;
import com.example.icecommtest.repositories.remote.AuthRepository;
import com.example.icecommtest.repositories.remote.MainRepository;

import java.util.List;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<ProductResponse>> productResponseMutableLiveData;
    private MutableLiveData<String> messageMutableLiveData;
    MainRepository mainRepository;

    private void init(){
        mainRepository = new MainRepository();
    }

    //Query product from category from view model
    public void productByCategory(String category){
        init();
        mainRepository.productByCategory(category);
    }

    //Create Observable for product query from category
    public LiveData<List<ProductResponse>> observeProductCategory(){
        if (productResponseMutableLiveData == null){
            productResponseMutableLiveData = MainRepository.productResponseMutableLiveData;
        }
        return productResponseMutableLiveData;
    }

    public LiveData<String> observeMessage(){
        if (messageMutableLiveData == null){
            messageMutableLiveData = MainRepository.messageMutableLiveData;
        }
        return messageMutableLiveData;
    }

}
