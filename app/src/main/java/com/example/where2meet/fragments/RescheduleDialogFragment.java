package com.example.where2meet.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.where2meet.R;
import com.example.where2meet.activities.ComposeActivity;
import com.example.where2meet.databinding.FragmentRescheduleDialogBinding;

import java.text.DateFormat;
import java.util.Calendar;


public class RescheduleDialogFragment extends DialogFragment{
    public FragmentRescheduleDialogBinding fragmentRescheduleDialogBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentRescheduleDialogBinding = FragmentRescheduleDialogBinding.inflate(inflater,container,false);
        fragmentRescheduleDialogBinding.btnCancelReschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        fragmentRescheduleDialogBinding.btnSelectReschuduledDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDatePicker();
            }
        });
        return fragmentRescheduleDialogBinding.getRoot();
    }

    public Dialog popDatePicker() {
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)
                getActivity(), year, month, dayOfMonth);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() -1000);
        return dialog;

    }

}