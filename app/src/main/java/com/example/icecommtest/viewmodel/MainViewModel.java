package com.example.icecommtest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.icecommtest.model.request.CartRequest;
import com.example.icecommtest.model.response.CartResponse;
import com.example.icecommtest.model.response.ProductResponse;
import com.example.icecommtest.repositories.remote.MainRepository;

import java.util.List;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<ProductResponse>> productResponseMutableLiveData;
    private MutableLiveData<List<String>> categoryResponseMutableLiveData;
    private MutableLiveData<String> messageMutableLiveData;
    private MutableLiveData<CartResponse> cartResponseMutableLiveData;
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

    public void categories(){
        init();
        mainRepository.categories();
    }

    //Create Observable for category
    public LiveData<List<String>> observeCategory(){
        if (categoryResponseMutableLiveData == null){
            categoryResponseMutableLiveData = MainRepository.categoryResponseMutableLiveData;
        }
        return categoryResponseMutableLiveData;
    }

    public void addCart(CartRequest cartRequest){
        init();
        mainRepository.addCart(cartRequest);
    }

    public LiveData<CartResponse> observeAddCart(){
        if (cartResponseMutableLiveData == null){
            cartResponseMutableLiveData = MainRepository.cartResponseMutableLiveData;
        }
        return cartResponseMutableLiveData;
    }

}
