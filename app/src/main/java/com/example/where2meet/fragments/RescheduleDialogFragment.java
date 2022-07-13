package com.example.where2meet.fragments;

import android.app.DatePickerDialog;
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


public class RescheduleDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    FragmentRescheduleDialogBinding fragmentRescheduleDialogBinding;

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

    public void popDatePicker() {
        DatePicker datePicker;
        datePicker = new DatePicker();
        datePicker.show(getChildFragmentManager(), "Date pick");
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        fragmentRescheduleDialogBinding.tvRescheduleDate.setText(selectedDate);
    }
}