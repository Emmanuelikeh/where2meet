package com.example.where2meet.models;


import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Groups")
public class Groups extends ParseObject {
    public static final String KEY_GROUP_NAME = "groupName";
    public static final String KEY_GROUP_MEMBERS = "groupMembers";


    public void setGroupName(String groupName){
        put(KEY_GROUP_NAME, groupName);
    }

    public void setGroupMembers(List<String> membersId){
        put(KEY_GROUP_MEMBERS, membersId);
    }

    public String getGroupName(){
        return getString(KEY_GROUP_NAME);
    }



}
