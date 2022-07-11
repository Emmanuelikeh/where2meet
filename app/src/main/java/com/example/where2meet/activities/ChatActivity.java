package com.example.where2meet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.where2meet.R;
import com.example.where2meet.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding activityChatBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatBinding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(activityChatBinding.getRoot());
    }
}