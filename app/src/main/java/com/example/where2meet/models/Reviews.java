package com.example.where2meet.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    public String getCreatedAt() throws ParseException {
        return  formatDateFromReviews(createdAt);
    }

    public String getCommentary(){
        return commentary;
    }


    private String formatDateFromReviews(String createdAt) throws ParseException {
        DateFormat parser = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
       Date parse =  parser.parse(createdAt);
       String formattedDate = formatter.format(parse);
       return  formattedDate;
    }

}
