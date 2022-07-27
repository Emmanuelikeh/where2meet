package com.example.where2meet.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static void presentMessageToUser(Context context, String message){
        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
    }
}
