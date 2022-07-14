package com.example.where2meet.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.where2meet.R;
import com.example.where2meet.activities.ChatActivity;
import com.example.where2meet.databinding.FragmentRescheduleDialogBinding;
import com.example.where2meet.models.Invite;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class RescheduleDialogFragment extends DialogFragment{
    public FragmentRescheduleDialogBinding fragmentRescheduleDialogBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentRescheduleDialogBinding = FragmentRescheduleDialogBinding.inflate(inflater, container, false);
        fragmentRescheduleDialogBinding.btnCancelReschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        fragmentRescheduleDialogBinding.btnSelectReschuduledDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog =  popDatePicker();
                dialog.show();
            }
        });
        fragmentRescheduleDialogBinding.btnUpdateNewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });
        return fragmentRescheduleDialogBinding.getRoot();
    }

    private void updateDate(){
        String newDate = fragmentRescheduleDialogBinding.tvNewDate.getText().toString();
        ChatActivity activity = (ChatActivity) getActivity();
        assert activity != null;
        Invite invite =  activity.getGroup();
        Date oldInvitationDate = invite.getInvitationDate();

        if(newDate.equals("")){
            Toast.makeText(getContext(),R.string.pick_date_message, Toast.LENGTH_SHORT).show();
            return;
        }
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String oldTime = dateFormat.format(oldInvitationDate);
        String dateWithTime = newDate + " " + oldTime;
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm", Locale.getDefault());
        try {
            Date newInvitationDate = format.parse(dateWithTime);
            invite.setInvitationDate(newInvitationDate);
            invite.saveInBackground(new SaveCallback() {
                @Override
                public void done(com.parse.ParseException e) {
                    if(e == null){
                        Toast.makeText(getContext(),R.string.saved_success, Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Dialog popDatePicker() {
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
            fragmentRescheduleDialogBinding.tvNewDate.setText(selectedDate);

        }
    }, year, month, dayOfMonth);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() -1000);
        return dialog;
}
}