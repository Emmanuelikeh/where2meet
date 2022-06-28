package com.example.where2meet.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.where2meet.R;
import com.example.where2meet.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {
    private FragmentProfileBinding fragmentProfileBinding;



    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater,container,false);
        View view = fragmentProfileBinding.getRoot();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentProfileBinding = null;
    }
}