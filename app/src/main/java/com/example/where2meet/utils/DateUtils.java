package com.example.where2meet.utils;

import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static void setDate(String pattern, TextView textView,@Nullable String text, Date date){
        if(text == null){
            text = "";
        }
        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        String strDate = dateFormat.format(date);
        String presentedDate = text + strDate;
        textView.setText(presentedDate);
    }
}
