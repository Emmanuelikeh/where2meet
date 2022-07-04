package com.example.where2meet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Reviews {
    String createdAt;
    String commentary;

    public Reviews(JSONObject jsonObject) throws JSONException {
        createdAt = jsonObject.getString("created_at");
        commentary = jsonObject.getString("text");

    }
    public static List<Reviews> fromJsonArray(JSONArray reviewsJsonArray) throws JSONException{
        List<Reviews> reviews = new ArrayList<>();
        for(int i =0; i < reviewsJsonArray.length(); i++){
            reviews.add(new Reviews(reviewsJsonArray.getJSONObject(i)));
        }
        return reviews;
    }

    public String getCreatedAt(){
        return  createdAt;
    }

    public String getCommentary(){
        return commentary;
    }
}
