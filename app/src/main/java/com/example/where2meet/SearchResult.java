package com.example.where2meet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    String name;
    String fsqId;
    int distance;
    String iconPrefix;
    String iconSuffix;
    String formattedAddress;
    String address;

    // no-arg, empty constructor required for Parceler
    public SearchResult() {}


    public SearchResult(JSONObject jsonObject) throws JSONException{
        name = jsonObject.getString("name");
        fsqId = jsonObject.getString("fsq_id");
        distance = jsonObject.getInt("distance");
        JSONArray categories = jsonObject.getJSONArray("categories");
        JSONObject firstItem = categories.getJSONObject(0);
        JSONObject icon = firstItem.getJSONObject("icon");
        iconPrefix = icon.getString("prefix");
        iconSuffix = icon.getString("suffix");

        JSONObject location = jsonObject.getJSONObject("location");
        formattedAddress = location.getString("formatted_address");
        address = location.getString("address");

    }


    public static List<SearchResult> fromJsonArray(JSONArray searchResultJsonArray) throws JSONException{
        List<SearchResult> searchResults = new ArrayList<>();
        for(int i =0; i < searchResultJsonArray.length(); i++){
            searchResults.add(new SearchResult(searchResultJsonArray.getJSONObject(i)));
        }
        return searchResults;
    }


    public String getName(){
        return name;
    }

    public String getFsqId(){
        return fsqId;
    }
    public  String getFormattedAddress(){
        return formattedAddress;
    }
    public  String getAddress(){
        return address;
    }
    public String getIcon() {
        return iconPrefix + 120 + iconSuffix;
    }

    public String getDistance(){
        return distance+" meters";
    }


}
