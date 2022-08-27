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
    }
}