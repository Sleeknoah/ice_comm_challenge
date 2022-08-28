package com.example.icecommtest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<String> category = new MutableLiveData<>();

    public void setFragmentType(String type){
        category.setValue(type);
    }

    public LiveData<String> observeSharedData(){
        return category;
    }

}
