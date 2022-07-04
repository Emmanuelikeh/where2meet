package com.example.where2meet;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;


@ParseClassName("Invite")
public class Invite extends ParseObject {

    public static final String KEY_ADDRESS = "Address";
    public static final String KEY_TIME = "Time";
    public static final String KEY_SENDER = "Sender";
    public static final String KEY_RECEIVER = "Receiver";
    public static final String KEY_INVITATION_DATE = "invitationDate";
    public static final String KEY_TITLE = "Title";
    public void setAddress(String address){
        put(KEY_ADDRESS,address);
    }

    public void setTime(Date time){
        put(KEY_TIME,time);

    }

    public void setSender(ParseUser sender){
        put(KEY_SENDER, sender);
    }

    public void setReceiver(ParseUser receiver){
        put(KEY_RECEIVER, receiver);
    }

    public  void setInvitationDate(Date invitationDate){
        put(KEY_INVITATION_DATE, invitationDate);
    }
    public  void setTitle(String title){
        put(KEY_TITLE,title);
    }


}
