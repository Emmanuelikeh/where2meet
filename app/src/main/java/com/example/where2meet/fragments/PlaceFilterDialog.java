package com.example.where2meet.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.where2meet.utils.CategoriesDictionary;
import com.example.where2meet.R;
import com.example.where2meet.databinding.FragmentPlacesFilterDialogBinding;

import java.util.Dictionary;

public class PlaceFilterDialog extends DialogFragment {
    private FragmentPlacesFilterDialogBinding fragmentPlacesFilterDialogBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentPlacesFilterDialogBinding = FragmentPlacesFilterDialogBinding.inflate(inflater,container,false);
        ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(getContext(), R.array.Categories,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> sortingOptionsAdapter = ArrayAdapter.createFromResource(getContext(),R.array.sortingOptions,android.R.layout.simple_spinner_item);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortingOptionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fragmentPlacesFilterDialogBinding.spinnerCategories.setAdapter(categoriesAdapter);
        fragmentPlacesFilterDialogBinding.spinnerSort.setAdapter(sortingOptionsAdapter);
        return fragmentPlacesFilterDialogBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentPlacesFilterDialogBinding.btnClosePlacesDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        fragmentPlacesFilterDialogBinding.radioCheapPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        fragmentPlacesFilterDialogBinding.radioModeratePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        fragmentPlacesFilterDialogBinding.radioPriceExpensive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });

        fragmentPlacesFilterDialogBinding.sbDistanceInMiles.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fragmentPlacesFilterDialogBinding.tvSeekBarProgress.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.radio_CheapPrice:
                if(checked)
                    break;
            case R.id.radio_ModeratePrice:
                if(checked)
                    break;
            case R.id.radio_PriceExpensive:
                if(checked)
                    break;
        }
    }
}
