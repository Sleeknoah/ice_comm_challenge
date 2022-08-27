package com.example.icecommtest.view.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.icecommtest.R;
import com.example.icecommtest.databinding.ActivityStoreBinding;
import com.example.icecommtest.utils.TranslucentScreenHelper;
import com.example.icecommtest.view.fragments.CartFragment;
import com.example.icecommtest.view.fragments.CategoryFragment;
import com.example.icecommtest.view.fragments.ProductFragment;
import com.example.icecommtest.view.fragments.ProfileFragment;

public class StoreActivity extends AppCompatActivity {

    private static final int PRODUCT = 0;
    private static final int CATEGORY = 1;
    private static final int CART = 2;
    private static final int PROFILE = 3;
    ActivityStoreBinding binding;
    private int current_frag = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoreBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        startFragment(PRODUCT);

        TranslucentScreenHelper.windowFlags(getWindow());

        //Load image into view
        Glide.with(getApplicationContext()).
                load("https://images.unsplash.com/photo-1494790108377-be9c29b29330")
                .into(binding.circleImageView);

        //Check items and set fragments
        binding.bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                startFragment(PRODUCT);
                return true;

            } else if (item.getItemId() == R.id.category) {
                startFragment(CATEGORY);
                return true;

            } else if (item.getItemId() == R.id.cart) {
                startFragment(CART);
                return true;

            } else if (item.getItemId() == R.id.profile_menu) {
                startFragment(PROFILE);
                return true;
            }
            return false;
        });
    }
    //Fragment contain is replaced here with current fragment
    public void startFragment(int tag) {
        current_frag = tag;
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container, setFragment(tag), null)
                .commit();
    }

    //Here the fragment switching takes place
    @SuppressLint("SetTextI18n")
    private Fragment setFragment(int tag) {
        switch (tag) {
            case PRODUCT:
                binding.fragmentName.setText("Home");
                return new ProductFragment();
            case CATEGORY:
                binding.fragmentName.setText("Category");
                return new CategoryFragment();
            case CART:
                binding.fragmentName.setText("Cart");
                return new CartFragment();
            case PROFILE:
                binding.fragmentName.setText("Profile");
                return new ProfileFragment();
        }
        return new ProductFragment();
    }
}