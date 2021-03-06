package com.example.where2meet.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Messages")
public class Messages extends ParseObject {

    public static final String KEY_GROUP_ID  = "groupId";
    public static final String KEY_MESSAGE_SENDER = "Sender";
    public static final String KEY_BODY = "Body";


    public void setGroupId(Invite invite){
        put(KEY_GROUP_ID, invite);
    }

    public void setMessageSender(ParseUser sender){
        put(KEY_MESSAGE_SENDER, sender);
    }

    public void setBody(String body){
        put(KEY_BODY, body);
    }

    public ParseUser getMessageSender(){
        return getParseUser(KEY_MESSAGE_SENDER);
    }

    public String getMessageBody(){
        return getString(KEY_BODY);
    }


}
