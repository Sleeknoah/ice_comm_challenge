package com.example.icecommtest.view.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.icecommtest.R;
import com.example.icecommtest.utils.TranslucentScreenHelper;

public class MainActivity extends AppCompatActivity {

    TextView sign_up;

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
    }
}