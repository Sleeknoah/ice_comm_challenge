package com.example.icecommtest.view.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.icecommtest.R;
import com.example.icecommtest.databinding.ActivityStoreBinding;
import com.example.icecommtest.utils.TranslucentScreenHelper;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoreActivity extends AppCompatActivity {

    ActivityStoreBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoreBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        TranslucentScreenHelper.windowFlags(getWindow());

        //Load image into view
        Glide.with(getApplicationContext()).
                load("https://images.unsplash.com/photo-1494790108377-be9c29b29330")
                .into(binding.circleImageView);
    }
}