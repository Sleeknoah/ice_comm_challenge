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
                setError(textInputLayout, "Valid email address required");
                return false;
            }
        }
        if (input.isEmpty() || input.equals(" ")){
            setError(textInputLayout, "Field cannot be empty");
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

    public static Boolean isPasswordValid(String input1, String input2,
                                   TextInputLayout pass1, TextInputLayout pass2){
        if (input1.length() < 4){
            setError(pass1, "Password cannot be less than four characters");
            return false;
        }else if (input2.length() < 4){
            setError(pass2, "Password cannot be less than four characters");
            return false;
        }

        if (!input1.equals(input2)){
            setError(pass1, "Passwords must match");
            return false;
        }
        pass1.setErrorEnabled(false);
        pass2.setErrorEnabled(false);
        return true;
    }

    private static void setError(TextInputLayout layout, CharSequence error){
        if (!layout.isErrorEnabled()){
            layout.setErrorEnabled(true);
            layout.setError(error);
        }
    }
    public static String capitalizeFirst(String word) {
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }
}
