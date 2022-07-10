package com.example.where2meet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.where2meet.utils.httpUtils;
import com.example.where2meet.models.Reviews;
import com.example.where2meet.adapters.ReviewsAdapter;
import com.example.where2meet.SearchResult;
import com.example.where2meet.databinding.ActivityDetailBinding;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding activityDetailBinding;
    private SearchResult searchResult;
    protected List<Reviews> reviewList;
    protected ReviewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(activityDetailBinding.getRoot());
        searchResult = (SearchResult) Parcels.unwrap(getIntent().getParcelableExtra(SearchResult.class.getSimpleName()));
        activityDetailBinding.tvFormattedAddress.setText(searchResult.getFormattedAddress());
        activityDetailBinding.tvDetailName.setText(searchResult.getName());
        String id = searchResult.getFsqId();
        String hostLink = formHostLink(id,"https://api.foursquare.com/v3/places/","/photos" );
        queryOtherDetails(hostLink);
        checkVisited();

        activityDetailBinding.flBtnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this,"this works", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(DetailActivity.this, ComposeActivity.class);
                i.putExtra("FormattedAddress", searchResult.getFormattedAddress());
                startActivity(i);
            }
        });

        activityDetailBinding.btnVisited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                currentUser.addAllUnique("Visited", Arrays.asList(searchResult.getFormattedAddress()));
                currentUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e != null){
                            Toast.makeText(DetailActivity.this, "Failed: " + e,Toast.LENGTH_SHORT).show();
                        }
                        else{
                            activityDetailBinding.btnVisited.setBackgroundColor(-65536);
                            activityDetailBinding.btnVisited.setText("ViSITED");
                        }
                    }
                });

            }
        });

        String reviewLink = formHostLink(id,"https://api.foursquare.com/v3/places/", "/tips");
        reviewList = new ArrayList<>();
        adapter = new ReviewsAdapter(this, reviewList);
        activityDetailBinding.rvReviews.setAdapter(adapter);
        activityDetailBinding.rvReviews.setLayoutManager(new LinearLayoutManager(DetailActivity.this));

        httpUtils.getRequest(reviewLink, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONArray jsonArray = json.jsonArray;
                try {
                    reviewList.addAll(Reviews.fromJsonArray(jsonArray));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("check this ", "Failed ");
            }
        });
    }

    private String formHostLink(String id, String baselink, String endlink) {
        return baselink + id + endlink;
    }


    private void queryOtherDetails(String hostLink) {
        httpUtils.getRequest(hostLink, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONArray jsonArray = json.jsonArray;
                try {
                    JSONObject firstItem = jsonArray.getJSONObject(0);
                    String imagePrefix = firstItem.getString("prefix");
                    String imageSuffix = firstItem.getString("suffix");
                    Glide.with(DetailActivity.this).load(imagePrefix+"original"+imageSuffix).centerCrop().into(activityDetailBinding.ivPlaceImage);
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


    private void checkVisited(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        JSONArray visited = currentUser.getJSONArray("Visited");
        if (visited != null ){
            for(int i= 0; i < visited.length(); i++){
                try {
                    if(visited.get(i).toString().equals(searchResult.getFormattedAddress())){
                        Log.i("check this", "objects: " + visited.get(i));
                        activityDetailBinding.btnVisited.setText("ViSITED");
                        activityDetailBinding.btnVisited.setBackgroundColor(-65536);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}