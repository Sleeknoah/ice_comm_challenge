package com.example.icecommtest.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.icecommtest.R;
import com.example.icecommtest.databinding.FragmentCartBinding;
import com.example.icecommtest.repositories.local.entity.Cart;
import com.example.icecommtest.view.adapters.CartAdapter;
import com.example.icecommtest.viewmodel.CartViewModel;

import java.util.List;

public class CartFragment extends Fragment {

    FragmentCartBinding binding;
    private CartViewModel cartViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        cartViewModel.getAllCart().observe(requireActivity(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                if (carts != null && !carts.isEmpty()){
                    CartAdapter adapter = new CartAdapter(requireActivity(), carts);
                    binding.cartRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    binding.cartRecycler.setAdapter(adapter);
                }else {
                    binding.cartRecycler.setVisibility(View.INVISIBLE);
                }
            }
        });


        return binding.getRoot();
    }
}