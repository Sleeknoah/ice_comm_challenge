package com.example.icecommtest.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.icecommtest.R;

public class CustomProgress extends ProgressDialog {
    Context mContext;
    ProgressDialog dialog;
    public CustomProgress(Context context) {
        super(context);
        this.mContext = context;
    }

    public void startProgress(String message){
        dialog = new ProgressDialog(mContext, R.style.DialogTheme);
        dialog.setCancelable(false);
        dialog.setMessage(message);
        dialog.show();
    }

    public void dismissDialog(){
        if (dialog != null) {
            dialog.dismiss();
        }

    }

}
