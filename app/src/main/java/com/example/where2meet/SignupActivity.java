package com.example.where2meet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.where2meet.databinding.ActivitySignupBinding;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding activitySignupBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignupBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = activitySignupBinding.getRoot();
        setContentView(view);


        activitySignupBinding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        activitySignupBinding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String signupUsername = activitySignupBinding.etSignupUsername.getText().toString();
                String signupEmail = activitySignupBinding.etSignupEmail.getText().toString();
                String signupPassword = activitySignupBinding.etSignupPassword.getText().toString();
                String signupRePassword = activitySignupBinding.etSignupRePassword.getText().toString();
                signUpUser(signupUsername,signupEmail,signupPassword,signupRePassword);
            }
        });


    }

    private void signUpUser(String signupUsername, String signupEmail, String signupPassword, String signupRePassword) {

        // check if password and re-entered passwords match
        if (!signupPassword.equals(signupRePassword)){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        ParseUser user = new ParseUser();

        //Set fields for the user to be created
        user.setEmail(signupEmail);
        user.setUsername(signupUsername);
        user.setPassword(signupPassword);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Toast.makeText(SignupActivity.this, "Account Created, Login!", Toast.LENGTH_SHORT).show();
                    ParseUser.logOut();
                    goLoginActivity();



                }
                else{
                    e.printStackTrace();
                }
            }
        });

    }

    private void goLoginActivity() {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
