package com.example.where2meet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.where2meet.databinding.ActivityDetailBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;


import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding activityDetailBinding;
    SearchResult searchResult;
    protected List<DetailActivity> detailActivityList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(activityDetailBinding.getRoot());
        searchResult = (SearchResult) Parcels.unwrap(getIntent().getParcelableExtra(SearchResult.class.getSimpleName()));
        activityDetailBinding.tvFormattedAddress.setText("Address: " + searchResult.getFormattedAddress());
        activityDetailBinding.tvDetailName.setText("Name: " + searchResult.getName());
        String id = searchResult.getFsqId();
        String hostLink = formHostLink(id,"https://api.foursquare.com/v3/places/","/photos" );
        queryOtherDetails(hostLink);

    }



    private String formHostLink(String id, String baselink, String endlink) {
        return baselink + id + endlink;
    }

    private void queryOtherDetails(String hostLink) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHeaders headers = new RequestHeaders();
        headers.put("Authorization", "fsq3HapICxpWstVvgaSOAlrzGLoCv7IypLnr82Q3c0AVzDk=");
        client.get(hostLink,headers,null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONArray jsonArray = json.jsonArray;
                try {
                    JSONObject firstItem = jsonArray.getJSONObject(0);
                    String imagePrefix = firstItem.getString("prefix");
                    String imageSuffix = firstItem.getString("suffix");
                    Glide.with(DetailActivity.this).load(imagePrefix+"original"+imageSuffix).override(1200,900).centerCrop().into(activityDetailBinding.ivPlaceImage);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i("check this", "onFailure");

            }
        });
    }



}