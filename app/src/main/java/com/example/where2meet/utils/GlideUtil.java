package com.example.where2meet.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.where2meet.R;
import com.parse.ParseFile;

public class GlideUtil {
    public static void getImage(int width,int height, ImageView view, ParseFile image, Context context){
        Glide.with(context).load(image == null? null : image.getUrl()).override(width, height).placeholder(R.drawable.ic_baseline_person_24).circleCrop().into(view);
    }
}
