package com.example.icecommtest.view.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icecommtest.R;
import com.example.icecommtest.model.request.LoginRequest;
import com.example.icecommtest.model.response.LoginResponse;
import com.example.icecommtest.utils.CustomProgress;
import com.example.icecommtest.utils.CustomSharedPreferences;
import com.example.icecommtest.utils.TranslucentScreenHelper;
import com.example.icecommtest.utils.ValidatorHelper;
import com.example.icecommtest.viewmodel.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout usernameLayout, passwordLayout;
    TextInputEditText username, password;
    TextView signInButton;
    CustomProgress progress;
    AuthViewModel authViewModel;
    private CustomSharedPreferences sharedPreferences;
    LiveData<LoginResponse> loginObservable;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize View Model
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        //initialize all view in a method below for cleaner codes
        initViews();
        TranslucentScreenHelper.windowFlags(getWindow());



        signInButton.setOnClickListener(v -> {
            if (ValidatorHelper.isInputSanitized(
                    Objects.requireNonNull(username.getText()).toString().trim(),
                    false, usernameLayout) && ValidatorHelper.isInputSanitized(
                    Objects.requireNonNull(password.getText()).toString().trim(),
                    false, passwordLayout)){
                //Run progress dialog while form details are sent to server through view model
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUsername(username.getText().toString().trim());
                loginRequest.setPassword(password.getText().toString().trim());
                authViewModel.loginUser(loginRequest);
                progress.startProgress("Please wait...");
                setLoginObservable();
            }
        });
    }

    //Here the next action is taken. Typically move to the Shopping screen
    //Also save the token for later use
    private void updateUI(String token) {
        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
        sharedPreferences.saveToken(token);
    }

    private void setLoginObservable(){
        //Livedata Observer is set here to observe change in the Login State
        loginObservable = authViewModel.observeUserLogin();

        loginObservable.observe(this, loginResponse -> {
            //dismiss progress dialog and check login response state here
            progress.dismissDialog();
            if (loginResponse != null){
                if (!loginResponse.getToken().equals("Error")){
                    updateUI(loginResponse.getToken());
                }else{
                    Toast.makeText(getApplicationContext(), "Login unsuccessful. Please username or password for errors", Toast.LENGTH_SHORT).show();
                }
                loginObservable.removeObservers(this);
            }
        });
    }

    private void initViews() {
        usernameLayout = findViewById(R.id.username_layout);
        passwordLayout = findViewById(R.id.password_layout);
        username = findViewById(R.id.usersname);
        password = findViewById(R.id.password);
        signInButton = findViewById(R.id.sign_in);
        progress = new CustomProgress(this);
        sharedPreferences = new CustomSharedPreferences(this);
    }


}