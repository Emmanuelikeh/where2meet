package com.example.where2meet.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.where2meet.R;
import com.example.where2meet.databinding.FragmentProfileBinding;
import com.example.where2meet.databinding.FragmentSearchBinding;


public class SearchFragment extends Fragment {
    private FragmentSearchBinding fragmentSearchBinding;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater,container,false);
        View view = fragmentSearchBinding.getRoot();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentSearchBinding = null;
    }
}