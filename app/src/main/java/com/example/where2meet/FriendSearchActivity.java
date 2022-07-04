package com.example.where2meet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.where2meet.databinding.ActivityFriendSearchBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FriendSearchActivity extends AppCompatActivity {

    public static final String TAG = "Friend";
    private ActivityFriendSearchBinding activityFriendSearchBinding;
    private FriendResultAdapter adapter;
    private List<ParseUser> parseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFriendSearchBinding = ActivityFriendSearchBinding.inflate(getLayoutInflater());
        setContentView(activityFriendSearchBinding.getRoot());
        parseUsers = new ArrayList<>();
        adapter = new FriendResultAdapter(this, parseUsers);
        activityFriendSearchBinding.rvFriendList.setAdapter(adapter);
        activityFriendSearchBinding.rvFriendList.setLayoutManager(new LinearLayoutManager(this));
        //first call to return a list of all friends
        queryUser("");
        activityFriendSearchBinding.btnFriendSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFriendSearch();
            }
        });
    }

    private void queryUser(String friend) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereContains("username", friend);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null){
                    parseUsers.addAll(objects);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void performFriendSearch(){
        String friendLook =  activityFriendSearchBinding.etFriendSearchBox.getText().toString();
        adapter.clear();
        queryUser(friendLook);
    }

}





