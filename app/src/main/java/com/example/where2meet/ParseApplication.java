package com.example.where2meet;

import android.app.Application;
import android.util.Log;

import com.example.where2meet.activities.MainActivity;
import com.example.where2meet.adapters.ChatAdapter;
import com.example.where2meet.models.Groups;
import com.example.where2meet.models.Invite;
import com.example.where2meet.models.Messages;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.SubscriptionHandling;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Register my parse models
        ParseObject.registerSubclass(Invite.class);
        ParseObject.registerSubclass(Groups.class);
        ParseObject.registerSubclass(Messages.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());
    }
}
