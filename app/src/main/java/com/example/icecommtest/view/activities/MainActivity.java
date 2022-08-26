package com.example.icecommtest.view.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.icecommtest.R;
import com.example.icecommtest.utils.CustomSharedPreferences;
import com.example.icecommtest.utils.TranslucentScreenHelper;

public class MainActivity extends AppCompatActivity {

    TextView sign_up;
    CustomSharedPreferences preferences;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sign_up = findViewById(R.id.sign_up);

        //Go to Login page when button is pressed
        sign_up.setOnClickListener(v -> startActivity(
                new Intent(getApplicationContext(), SignUpActivity.class)));

        //Make StatusBar Translucent
        Window window = getWindow();
        TranslucentScreenHelper.windowFlagsDarkStatusBar(window);

        preferences = new CustomSharedPreferences(this);

        //Check to see if user is logged in and act accordingly
        //We know this if the user token is string that is not 0, Error or null

        if (preferences.getSavedToken().equals("0") ||
                preferences.getSavedToken().equals("Error") || preferences.getSavedToken() == null){
            //Go to Sign Up page when button is pressed
            sign_up.setOnClickListener(v -> startActivity(
                    new Intent(getApplicationContext(), SignUpActivity.class)));
        }else{
            //Change button text
            //Go to Store page when button is pressed
            sign_up.setText("Start Shopping");
            sign_up.setOnClickListener(v -> startActivity(
                    new Intent(getApplicationContext(), StoreActivity.class)));
        }
    }
}