package com.example.where2meet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.where2meet.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = activityLoginBinding.getRoot();
        setContentView(view);

        // In order to avoid repetition in the loginIn process
        // Check if there's a user logged in
        // if there is, take them to MainActivity
        if(ParseUser.getCurrentUser() != null){
            // getCurrentUser would be null if user is not logged in already
            goMainActivity();
        }

        activityLoginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = activityLoginBinding.etUserName.getText().toString();
                String password = activityLoginBinding.etPassword.getText().toString();
                logInUser(username,password);
            }
        });

        activityLoginBinding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void logInUser(String username, String password){
        // preferred to ParseUser.logIn for better user experience
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                // parse always takes in exception, if there wasn't any problem
                // the exception will be null;
                if(e != null) {
                    Toast.makeText(LoginActivity.this, "Issue with Login", Toast.LENGTH_SHORT).show();
                    return;
                }
                goMainActivity();
                Toast.makeText(LoginActivity.this,"Login Success", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void goMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        // to close out the LoginActivity, so the user does not come back to the login in page when successfully logged in
        finish();
    }
}