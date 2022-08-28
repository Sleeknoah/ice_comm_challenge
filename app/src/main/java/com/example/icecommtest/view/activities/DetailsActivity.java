package com.example.icecommtest.view.activities;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.icecommtest.databinding.ActivityDetailsBinding;
import com.example.icecommtest.model.request.CartRequest;
import com.example.icecommtest.model.request.ProductsItem;
import com.example.icecommtest.model.response.CartResponse;
import com.example.icecommtest.repositories.local.entity.Cart;
import com.example.icecommtest.utils.CustomProgress;
import com.example.icecommtest.utils.CustomSharedPreferences;
import com.example.icecommtest.utils.TranslucentScreenHelper;
import com.example.icecommtest.viewmodel.CartViewModel;
import com.example.icecommtest.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;
    private CustomProgress progress;
    private CustomSharedPreferences sharedPreferences;
    MainViewModel mainViewModel;
    private LiveData<CartResponse> cartObservable;
    private CartViewModel cartViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view  = binding.getRoot();
        setContentView(view);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        cartObservable = mainViewModel.observeAddCart();


        TranslucentScreenHelper.windowFlagsDarkStatusBar(getWindow());

        //initialize all view in a method below for cleaner codes
        initViews();

        //Retrieve sent data from product adapter here
        binding.productTitle.setText(getIntent().getStringExtra("title"));
        binding.description.setText(getIntent().getStringExtra("desc"));
        String decimals = getIntent().getStringExtra("cost").split("\\.")[1];
        String cost = null;
        if (decimals.length() == 1){
            cost = getIntent().getStringExtra("cost") + "0";
        }else{
            cost = getIntent().getStringExtra("cost");
        }
        binding.cost.setText(cost);

        //Load image into view
        Glide.with(getApplicationContext()).
                load(getIntent().getStringExtra("image"))
                .into(binding.image);

        binding.add.setOnClickListener(v -> {
            String qty = binding.qty.getText().toString();
            addToQuantity(Integer.parseInt(qty));
        });

        binding.remove.setOnClickListener(v -> {
            String qty = binding.qty.getText().toString();
            removeQuantity(Integer.parseInt(qty));
        });

        setCartObservable();

        binding.addCart.setOnClickListener(v -> {
                mainViewModel.addCart(
                        cartRequest(getIntent()
                                        .getIntExtra("id", 0),
                                Integer.parseInt(binding.qty.getText().toString())
                        ));
                progress.startProgress("Adding to cart...");
        });



    }

    private void setCartObservable() {
        cartObservable.observe(this, cartResponses -> {
            progress.dismissDialog();
            if (cartResponses != null) {
                if (!cartResponses.getProducts().isEmpty()){
                    Toast.makeText(DetailsActivity.this.getApplicationContext(), "Product added to cart", Toast.LENGTH_SHORT).show();
                    Cart cart = new Cart(
                            DetailsActivity.this.getIntent().getStringExtra("title"),
                            DetailsActivity.this.getIntent().getStringExtra("cost"),
                            DetailsActivity.this.getIntent().getStringExtra("image"),
                            Integer.parseInt(binding.qty.getText().toString()));
                    cartViewModel.insert(cart);
                    cartResponses.setProducts(new ArrayList<>());
                }
            }
        });
    }

    private CartRequest cartRequest(int productId, int qty){
        CartRequest cartRequest = new CartRequest();
        ProductsItem productsItem = new ProductsItem();
        productsItem.setProductId(productId);
        productsItem.setQuantity(qty);
        List<ProductsItem> productsItemList = new ArrayList<>();
        productsItemList.add(productsItem);
        cartRequest.setUserId(Integer.parseInt(sharedPreferences.getSavedToken()));
        cartRequest.setProducts(productsItemList);
        cartRequest.setDate("22-06-2022");
        return cartRequest;
    }

    private void addToQuantity(int qty){
        int newQty = qty + 1;
        binding.qty.setText(String.valueOf(newQty));
    }
    private void removeQuantity(int qty){
        if (qty > 1){
            int newQty = qty - 1;
            binding.qty.setText(String.valueOf(newQty));
        }
    }

    private void initViews() {
        progress = new CustomProgress(this);
        sharedPreferences = new CustomSharedPreferences(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}