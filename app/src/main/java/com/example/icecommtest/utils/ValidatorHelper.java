package com.example.icecommtest.utils;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidatorHelper {

    private ValidatorHelper() {
    }

    public static Boolean isInputSanitized(String input, Boolean isEmail, TextInputLayout textInputLayout){
        if(isEmail){
            if (!isEmailSanitized(input)){
                textInputLayout.setError("Valid email address required");
                return false;
            }
        }
        if (input.isEmpty() || input.equals(" ")){
            textInputLayout.setError("Field cannot be empty");
            return false;
        }
        textInputLayout.setErrorEnabled(false);
        return true;
    }

    private static boolean isEmailSanitized(String input){
        String expression = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input.trim());
        return matcher.matches();
    }
}
