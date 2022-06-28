package com.example.where2meet.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.where2meet.R;
import com.example.where2meet.databinding.FragmentCalendarBinding;
import com.example.where2meet.databinding.FragmentProfileBinding;


public class CalendarFragment extends Fragment {
    private FragmentCalendarBinding fragmentCalendarBinding;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCalendarBinding = FragmentCalendarBinding.inflate(inflater,container,false);
        View view = fragmentCalendarBinding.getRoot();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentCalendarBinding = null;
    }
}