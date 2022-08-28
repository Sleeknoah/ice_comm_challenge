package com.example.icecommtest.view.fragments;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.icecommtest.R;
import com.example.icecommtest.databinding.FragmentProfileBinding;
import com.example.icecommtest.model.pojo.Address;
import com.example.icecommtest.model.pojo.Geolocation;
import com.example.icecommtest.model.pojo.Name;
import com.example.icecommtest.model.request.SignUpRequest;
import com.example.icecommtest.model.response.SignUpResponse;
import com.example.icecommtest.utils.CustomProgress;
import com.example.icecommtest.utils.CustomSharedPreferences;
import com.example.icecommtest.utils.ValidatorHelper;
import com.example.icecommtest.view.activities.MainActivity;
import com.example.icecommtest.view.activities.SignUpActivity;
import com.example.icecommtest.view.activities.StoreActivity;
import com.example.icecommtest.viewmodel.AuthViewModel;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    private CustomProgress progress;
    private AuthViewModel authViewModel;
    private CustomSharedPreferences sharedPreferences;
    Boolean isFirstNameValid, isLastNameValid, isUserValid, isEmailValid, isPhoneValid;
    private LiveData<SignUpResponse> updateObservable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //initialize all view in a method below for cleaner codes
        initViews();
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        //Load image into view
        Glide.with(getApplicationContext()).
                load("https://images.unsplash.com/photo-1494790108377-be9c29b29330")
                .into(binding.circleImageView);

        //Set all edit texts to save value
        binding.firstname.setText(sharedPreferences.getUsers().getName().getFirstname());
        binding.lastname.setText(sharedPreferences.getUsers().getName().getLastname());
        binding.usersname.setText(sharedPreferences.getUsers().getUsername());
        binding.email.setText(sharedPreferences.getUsers().getEmail());
        binding.phone.setText(sharedPreferences.getUsers().getPhone());

        binding.update.setOnClickListener(v -> {
            if (sanitizeInputs()){
                authViewModel.updateUser(
                        Integer.parseInt(sharedPreferences.getSavedToken()),
                        signUpRequest());
                progress.startProgress("Please wait...");
            }
        });

        //Livedata Observer is set here to observe change in the Login State
        updateObservable = authViewModel.observeUserUpdate();

        updateObservable.observe(requireActivity(), signUpResponse -> {
            //dismiss progress dialog and check login response state here
            progress.dismissDialog();
            if (signUpResponse != null) {
                if (signUpResponse.getId() != 0) {
                    updateUI();
                } else {
                    Toast.makeText(getApplicationContext(), "Update unsuccessful. Please try again", Toast.LENGTH_SHORT).show();
                }
                updateObservable.removeObservers(this);
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logout user here
                //Clear id and user details
                sharedPreferences.clearPref(getString(R.string.token));
                sharedPreferences.clearPref(getString(R.string.user_details));
                logoutUpdateUI();
            }
        });


        return binding.getRoot();
    }

    private void logoutUpdateUI() {
        //Go back to start screen
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void updateUI() {
        sharedPreferences.saveUser(signUpRequest());
    }

    private void initViews() {
        progress = new CustomProgress(requireContext());
        sharedPreferences = new CustomSharedPreferences(requireContext());
    }



    //Set up request for update api
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
        signUpRequest.setAddress(address);

        return signUpRequest;
    }

    //Use Validator Helper to validate all inputs
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

        return isFirstNameValid && isLastNameValid && isUserValid && isEmailValid && isPhoneValid;
    }
}