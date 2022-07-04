package com.example.where2meet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.where2meet.databinding.ActivityComposeBinding;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ComposeActivity extends AppCompatActivity {

    ActivityComposeBinding activityComposeBinding;
    private final int REQUEST_CODE = 20;
    List<ParseUser> receiversList = new ArrayList<>();

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
        Date inviteDate = getDate();
        Date inviteTime = getTime();
        String title = activityComposeBinding.etComposeTitle.getText().toString();
        String address = getFormattedAddress();
        ParseUser receiver = receiversList.get(0);
        ParseUser sender = ParseUser.getCurrentUser();

        Invite invite = new Invite();
        invite.setInvitationDate(inviteDate);
        invite.setSender(sender);
        invite.setTime(inviteTime);
        invite.setTitle(title);
        invite.setReceiver(receiver);
        invite.setAddress(address);

        invite.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if(e != null){
                    Toast.makeText(ComposeActivity.this,"Error while saving ",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ComposeActivity.this,"success ",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ComposeActivity.this, DetailActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private Date getDate() throws ParseException {
        String date = activityComposeBinding.etComposeDate.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        return dateFormat.parse(date);
    }

    private Date getTime() throws ParseException {
        String time = activityComposeBinding.etComposeTime.getText().toString();
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        return timeFormat.parse(time);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            // Get parseUser object
            ParseUser parseUser = (ParseUser) data.getExtras().get("parseUser");
            //Modify datasource to include parseUser;
            activityComposeBinding.etComposerUsername.setText(parseUser.getUsername());
            receiversList.add(0,parseUser);
            Log.i("check this", "length: " + receiversList.size());
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
        if(activityComposeBinding.etComposeDate.getText().toString().equals("")){
            Toast.makeText(ComposeActivity.this, "please enter a valid date", Toast.LENGTH_SHORT).show();
            return;
        }
        if(activityComposeBinding.etComposeTime.getText().toString().equals("")){
            Toast.makeText(ComposeActivity.this, "please enter a valid time", Toast.LENGTH_SHORT).show();
            return;
        }
        querySend();
    }


    private String getFormattedAddress(){
        return (String) getIntent().getExtras().get("FormattedAddress");
    }
}