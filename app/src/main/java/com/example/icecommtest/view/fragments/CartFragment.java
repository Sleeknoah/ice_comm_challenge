package com.example.icecommtest.view.fragments;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        cartViewModel.getAllCart().observe(getViewLifecycleOwner(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                if (carts != null && !carts.isEmpty()){
                    CartAdapter adapter = new CartAdapter(requireContext(), carts);
                    binding.cartRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    binding.cartRecycler.setAdapter(adapter);
                    binding.cartRecycler.addItemDecoration(new BottomItemDecorator());
                }else {
                    binding.cartRecycler.setVisibility(View.INVISIBLE);
                }
            }
        });


        return binding.getRoot();
    }

    class BottomItemDecorator extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (parent.getChildAdapterPosition(view) == state.getItemCount() - 1){
                outRect.bottom = 80;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cartViewModel.getAllCart().removeObservers(getViewLifecycleOwner());
    }
}