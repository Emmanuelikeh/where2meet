package com.example.where2meet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.where2meet.R;
import com.example.where2meet.adapters.ChatAdapter;
import com.example.where2meet.databinding.ActivityChatBinding;
import com.example.where2meet.models.Groups;
import com.example.where2meet.models.Messages;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.SubscriptionHandling;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding activityChatBinding;
    private List<Messages> messagesList;
    private ChatAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatBinding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(activityChatBinding.getRoot());
        activityChatBinding.btnSendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processMessage();
            }
        });
        messagesList = new ArrayList<>();
        adapter = new ChatAdapter(ChatActivity.this,messagesList);
        activityChatBinding.rvChats.setAdapter(adapter);
        activityChatBinding.rvChats.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        queryChats();
        liveQueries();
    }

    private void liveQueries() {
        String websocketUrl = "wss://where2meetemmanuel.b4a.io";
        ParseLiveQueryClient parseLiveQueryClient = null;
        try {
            parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient(new URI(websocketUrl));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ParseQuery<Messages> parseQuery = ParseQuery.getQuery(Messages.class);
        parseQuery.include(Messages.KEY_MESSAGE_SENDER);
        Groups currentGroup = getGroup();
        parseQuery.whereEqualTo(Messages.KEY_GROUP_ID,currentGroup);
        SubscriptionHandling<Messages> subscriptionHandling = parseLiveQueryClient.subscribe(parseQuery);
        subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, new SubscriptionHandling.HandleEventCallback<Messages>() {
            @Override
            public void onEvent(ParseQuery<Messages> query, Messages object) {
                messagesList.add(object);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        activityChatBinding.rvChats.scrollToPosition(messagesList.size()-1);
                    }
                });
            }
        });
    }


    private void processMessage() {
        String userInputChat = activityChatBinding.etChatBox.getText().toString();
        Groups specificGroup = getGroup();
        if(userInputChat.equals("")){
            Toast.makeText(this,getString(R.string.empty_chat_message),Toast.LENGTH_SHORT).show();
            return;
        }
        Messages messages = new Messages();
        messages.setMessageSender(ParseUser.getCurrentUser());
        messages.setGroup(specificGroup);
        messages.setBody(userInputChat);
        messages.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Toast.makeText(ChatActivity.this, getString(R.string.sending_failure),Toast.LENGTH_SHORT).show();
                    return;
                }
                activityChatBinding.etChatBox.setText("");
            }
        });
    }

    private Groups getGroup() {
        return (Groups) getIntent().getExtras().get("groupInfo");
    }

    private void queryChats() {
        ParseQuery<Messages> query = ParseQuery.getQuery(Messages.class);
        Groups currentGroup = getGroup();
        query.whereEqualTo(Messages.KEY_GROUP_ID,currentGroup);
        query.include(Messages.KEY_MESSAGE_SENDER);
        query.findInBackground(new FindCallback<Messages>() {
            @Override
            public void done(List<Messages> objects, ParseException e) {
                if(e != null){
                    Toast.makeText(ChatActivity.this,getString(R.string.error_info), Toast.LENGTH_SHORT).show();
                    return;
                }
                messagesList.addAll(objects);
                activityChatBinding.rvChats.scrollToPosition(messagesList.size()-1);
                adapter.notifyDataSetChanged();
            }
        });

    }






}