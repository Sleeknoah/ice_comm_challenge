package com.example.icecommtest.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.icecommtest.databinding.FragmentProductBinding;
import com.example.icecommtest.databinding.FragmentProductCategoryBinding;
import com.example.icecommtest.model.response.ProductResponse;
import com.example.icecommtest.utils.ValidatorHelper;
import com.example.icecommtest.view.activities.StoreActivity;
import com.example.icecommtest.view.adapters.ProductAdapter;
import com.example.icecommtest.viewmodel.MainViewModel;
import com.example.icecommtest.viewmodel.SharedViewModel;

import java.util.List;


public class ProductCategoryFragment extends Fragment {

    private FragmentProductCategoryBinding binding;
    private ProductAdapter adapter;
    private MainViewModel mainViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        //Check sharedViewModel and effect change in radio group
        sharedViewModel.observeSharedData().observe(this,category -> {
            if (category != null && activity != null) {
                ((StoreActivity)activity).changeTitle(ValidatorHelper.capitalizeFirst(category));
                updateUI(category);
            }
        });
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductCategoryBinding.inflate(inflater, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        return binding.getRoot();
    }

    private void updateUI(String category) {
        makeRequest(category);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void makeRequest(String category){
        binding.productRecycler.setVisibility(View.INVISIBLE);
        mainViewModel.productByCategory(category);
        setProductObservable();
    }
    private void setProductObservable() {
        //Livedata Observer is set here to observe change in the product response State
        LiveData<List<ProductResponse>> productObservable = mainViewModel.observeProductCategory();

        mainViewModel.observeMessage().observe(getViewLifecycleOwner(), s -> {
            if(s != null){
                if (s.equals("error")){
                    Toast.makeText(requireContext(), "Error loading products", Toast.LENGTH_SHORT).show();
                }

            }
        });

        productObservable.observe(getViewLifecycleOwner(), productResponse -> {
            if (productResponse != null){
                adapter = new ProductAdapter(requireActivity(), productResponse);
                // setting grid layout manager to implement grid view.
                // in this method '2' represents number of columns to be displayed in grid view.
                GridLayoutManager layoutManager=new GridLayoutManager(requireContext(),2);

                // at last set adapter to recycler view.
                binding.productRecycler.setLayoutManager(layoutManager);
                binding.productRecycler.setAdapter(adapter);

                binding.productRecycler.setVisibility(View.VISIBLE);
            }
        });
    }
}