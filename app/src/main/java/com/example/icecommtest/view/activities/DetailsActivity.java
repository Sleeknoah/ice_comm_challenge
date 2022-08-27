package com.example.icecommtest.view.activities;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.icecommtest.databinding.ActivityDetailsBinding;
import com.example.icecommtest.utils.TranslucentScreenHelper;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view  = binding.getRoot();
        setContentView(view);

        TranslucentScreenHelper.windowFlagsDarkStatusBar(getWindow());

        //Retrieve sent data from product adapter here
        binding.productTitle.setText(getIntent().getStringExtra("title"));
        binding.description.setText(getIntent().getStringExtra("desc"));
        binding.cost.setText(getIntent().getStringExtra("cost"));
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

    }

    private void addToQuantity(int qty){
        int newQty = qty + 1;
        binding.qty.setText(String.valueOf(newQty));
    }
    private void removeQuantity(int qty){
        if (qty > 0){
            int newQty = qty - 1;
            binding.qty.setText(String.valueOf(newQty));
        }
    }
}