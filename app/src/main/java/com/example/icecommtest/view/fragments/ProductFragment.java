package com.example.icecommtest.view.fragments;

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
import com.example.icecommtest.model.response.ProductResponse;
import com.example.icecommtest.view.adapters.ProductAdapter;
import com.example.icecommtest.viewmodel.MainViewModel;

import java.util.List;


public class ProductFragment extends Fragment {

    private FragmentProductBinding binding;
    private ProductAdapter adapter;
    private MainViewModel mainViewModel;
    String category;
    RadioButton radioButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(inflater, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        View view = binding.getRoot();

        radioButton = view.findViewById(binding.radio.getCheckedRadioButtonId());

        binding.radio.setOnCheckedChangeListener((group, checkedId) -> {
            radioButton = view.findViewById(checkedId);
            category = radioButton.getText().toString().toLowerCase();
            makeRequest(category);
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Make api call on resume so we can always get new update
        //Get text of selected button and save as category and make call
        category = radioButton.getText().toString().toLowerCase();
        makeRequest(category);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
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

                productObservable.removeObservers(requireActivity());
            }
        });
    }
}