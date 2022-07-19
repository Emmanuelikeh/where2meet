package com.example.where2meet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.where2meet.databinding.FragmentPlacesFilterDialogBinding;

public class PlaceFilterDialog extends DialogFragment {
    private FragmentPlacesFilterDialogBinding fragmentPlacesFilterDialogBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentPlacesFilterDialogBinding = FragmentPlacesFilterDialogBinding.inflate(inflater,container,false);
        fragmentPlacesFilterDialogBinding.btnClosePlacesDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return fragmentPlacesFilterDialogBinding.getRoot();
    }
}
