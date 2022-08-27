package com.example.icecommtest.view.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.icecommtest.databinding.ActivitySignUpBinding;
import com.example.icecommtest.model.pojo.Address;
import com.example.icecommtest.model.pojo.Geolocation;
import com.example.icecommtest.model.pojo.Name;
import com.example.icecommtest.model.request.SignUpRequest;
import com.example.icecommtest.model.response.SignUpResponse;
import com.example.icecommtest.utils.CustomProgress;
import com.example.icecommtest.utils.CustomSharedPreferences;
import com.example.icecommtest.utils.TranslucentScreenHelper;
import com.example.icecommtest.utils.ValidatorHelper;
import com.example.icecommtest.viewmodel.AuthViewModel;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    CustomProgress progress;
    AuthViewModel authViewModel;
    Boolean isFirstNameValid, isLastNameValid, isUserValid, isEmailValid;
    Boolean isPhoneValid, isPasswordValid;
    private CustomSharedPreferences sharedPreferences;
    LiveData<SignUpResponse> registerObservable;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Initialize View Model
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        //initialize all view in a method below for cleaner codes
        initViews();
        TranslucentScreenHelper.windowFlags(getWindow());

        binding.signIn.setOnClickListener(v -> {
            if (sanitizeInputs()){
                authViewModel.registerUser(signUpRequest());
                progress.startProgress("Please wait...");
                setRegisterObservable();
            }
        });

        //Go to sign in if user has account
        binding.gotoSignIn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));

    }

    private void setRegisterObservable() {
        //Livedata Observer is set here to observe change in the Login State
        registerObservable = authViewModel.observeUserSignUp();

        registerObservable.observe(this, signUpResponse -> {
            //dismiss progress dialog and check login response state here
            progress.dismissDialog();
            if (signUpResponse != null) {
                if (signUpResponse.getId() != 0) {
                    updateUI(signUpResponse.getId());
                } else {
                    Toast.makeText(SignUpActivity.this.getApplicationContext(), "Registration unsuccessful. Please try again", Toast.LENGTH_SHORT).show();
                }
                registerObservable.removeObservers(this);
            }
        });
    }

    //Save user details
    private void updateUI(int id) {
        sharedPreferences.saveToken(String.valueOf(id));
        sharedPreferences.saveUser(signUpRequest());
        Intent intent = new Intent(this, StoreActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private SignUpRequest signUpRequest() {
        SignUpRequest signUpRequest =  new SignUpRequest();
        Name name = new Name();
        name.setFirstname(Objects.requireNonNull(binding.firstname.getText()).toString());
        name.setLastname(Objects.requireNonNull(binding.lastname.getText()).toString());
        Geolocation geolocation  = new Geolocation();
        geolocation.setLat("5.99983");
        geolocation.setLng("5.973664");
        Address address = new Address();
        address.setNumber(2);
        address.setStreet("Mushin");
        address.setCity("Ikorodu");
        address.setZipcode("2384884");
        address.setGeolocation(geolocation);

        signUpRequest.setName(name);
        signUpRequest.setUsername(Objects.requireNonNull(binding.usersname.getText()).toString());
        signUpRequest.setEmail(Objects.requireNonNull(binding.email.getText()).toString());
        signUpRequest.setPhone(Objects.requireNonNull(binding.phone.getText()).toString());
        signUpRequest.setPassword(Objects.requireNonNull(binding.password.getText()).toString());
        signUpRequest.setAddress(address);

        return signUpRequest;
    }

    private void initViews() {
        progress = new CustomProgress(this);
        sharedPreferences = new CustomSharedPreferences(this);
    }

    private Boolean sanitizeInputs(){
        isFirstNameValid = ValidatorHelper.isInputSanitized(
                Objects.requireNonNull(binding.firstname.getText()).toString(),
                false, binding.firstnameLayout);
        isLastNameValid = ValidatorHelper.isInputSanitized(
                Objects.requireNonNull(binding.lastname.getText()).toString(),
                false, binding.lastnameLayout);

        isUserValid = ValidatorHelper.isInputSanitized(
                Objects.requireNonNull(binding.usersname.getText()).toString(),
                false, binding.usernameLayout);

        isEmailValid = ValidatorHelper.isInputSanitized(
                Objects.requireNonNull(binding.email.getText()).toString(),
                true, binding.emailLayout);

        isPhoneValid = ValidatorHelper.isInputSanitized(
                Objects.requireNonNull(binding.phone.getText()).toString(),
                false, binding.phoneLayout);

        isPasswordValid = ValidatorHelper.isPasswordValid(
                Objects.requireNonNull(binding.password.getText()).toString(),
                Objects.requireNonNull(binding.confirm.getText()).toString(),
                binding.passwordLayout,
                binding.confirmLayout);

        return isFirstNameValid && isLastNameValid && isUserValid && isEmailValid && isPhoneValid && isPasswordValid;
    }
}