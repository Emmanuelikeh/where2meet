package com.example.where2meet.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.where2meet.R;
import com.example.where2meet.fragments.DatePicker;

import com.example.where2meet.models.Invite;
import com.example.where2meet.databinding.ActivityComposeBinding;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ComposeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private ActivityComposeBinding activityComposeBinding;
    private final int REQUEST_CODE = 20;
    private List<ParseUser> receiversList = new ArrayList<>();
    private int hour, minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComposeBinding = ActivityComposeBinding.inflate(getLayoutInflater());
        setContentView(activityComposeBinding.getRoot());
        String formattedAddress = getFormattedAddress();
        activityComposeBinding.etComposeAddress.setText(formattedAddress);

        activityComposeBinding.btnFriendSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ComposeActivity.this, FriendSearchActivity.class);
                startActivityForResult(i, REQUEST_CODE);
            }
        });

        activityComposeBinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // check to see if the user has provided all needed information and proceeds to update the database
                    inviteCheck();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void querySend() throws ParseException {
        Date inviteDate = getDateAndTime();
        String title = activityComposeBinding.etComposeTitle.getText().toString();
        String address = getFormattedAddress();
        ParseUser receiver = receiversList.get(0);
        ParseUser sender = ParseUser.getCurrentUser();

        Invite invite = new Invite();
        invite.setInvitationDate(inviteDate);
        invite.setSender(sender);
        invite.setTitle(title);
        invite.setReceiver(receiver);
        invite.setAddress(address);
        invite.setFlag(null);
        invite.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if(e != null){
                    Toast.makeText(ComposeActivity.this,getString(R.string.saving_error_message),Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ComposeActivity.this,getString(R.string.saved_success),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ComposeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private Date getDateAndTime() throws ParseException {
        String date = activityComposeBinding.tvComposeDate.getText().toString();
        String time = activityComposeBinding.tvComposeTime.getText().toString();
        String dateWithTime = date + " " + time;
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm", Locale.getDefault());
        return dateFormat.parse(dateWithTime);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            // Get parseUser object
            ParseUser parseUser = (ParseUser) data.getExtras().get("parseUser");
            //Modify datasource to include parseUser;
            activityComposeBinding.etComposerUsername.setText(parseUser.getUsername());
            if(receiversList.size() < 1){
                receiversList.add(0,parseUser);
            }
            else{
                Toast.makeText(this,getString(R.string.multiple_user_warning), Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void inviteCheck() throws ParseException {
        if(receiversList.size() == 0){
            Toast.makeText(ComposeActivity.this, "please select a user", Toast.LENGTH_SHORT).show();
            return;
        }
        if(activityComposeBinding.etComposeTitle.getText().toString().equals("")){
            Toast.makeText(ComposeActivity.this, "please enter a valid title", Toast.LENGTH_SHORT).show();
            return;
        }
        if(activityComposeBinding.tvComposeDate.getText().toString().equals("")){
            Toast.makeText(ComposeActivity.this, "please enter a valid date", Toast.LENGTH_SHORT).show();
            return;
        }
        if(activityComposeBinding.tvComposeTime.getText().toString().equals("")){
            Toast.makeText(ComposeActivity.this, "please enter a valid time", Toast.LENGTH_SHORT).show();
            return;
        }
        querySend();
    }


    private String getFormattedAddress(){
        return (String) getIntent().getExtras().get("FormattedAddress");
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                activityComposeBinding.tvComposeTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour,minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute,true);
        timePickerDialog.setTitle(getString(R.string.Select_Time));
        timePickerDialog.show();
    }

    public void popDatePicker(View view) {
        DatePicker datePicker;
        datePicker = new DatePicker();
        datePicker.show(getSupportFragmentManager(), "Date pick");
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        activityComposeBinding.tvComposeDate.setText(selectedDate);
    }
}