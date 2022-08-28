package com.example.icecommtest.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.icecommtest.databinding.FragmentCategoryBinding;
import com.example.icecommtest.utils.ValidatorHelper;
import com.example.icecommtest.view.activities.StoreActivity;
import com.example.icecommtest.view.adapters.CategoryAdapter;
import com.example.icecommtest.viewmodel.MainViewModel;
import com.example.icecommtest.viewmodel.SharedViewModel;

import java.util.List;


public class CategoryFragment extends Fragment {

    FragmentCategoryBinding binding;
    MainViewModel mainViewModel;
    private CategoryAdapter adapter;
    SharedViewModel sharedViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        //Make call to endpoint
        mainViewModel.categories();
        observeCategory();

        return binding.getRoot();
    }

    private void observeCategory() {
        LiveData<List<String>> observable = mainViewModel.observeCategory();
        observable.observe(getViewLifecycleOwner(), strings -> {
            binding.progressBar.setVisibility(View.INVISIBLE);
            if (strings != null){
                adapter = new CategoryAdapter(requireActivity(), strings);
                // setting grid layout manager to implement grid view.
                // in this method '2' represents number of columns to be displayed in grid view.
                LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());

                // at last set adapter to recycler view.
                binding.categoryRecycler.setLayoutManager(layoutManager);
                binding.categoryRecycler.setAdapter(adapter);
                binding.categoryRecycler.setVisibility(View.VISIBLE);

                adapter.setOnItemClickListener(position -> {
                    String category = strings.get(position).toLowerCase();
                    sharedViewModel.setFragmentType(category);
                    ((StoreActivity)requireActivity()).implementFragmentChange(4);
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((StoreActivity)requireActivity()).changeTitle("Category");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}