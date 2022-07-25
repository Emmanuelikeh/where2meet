package com.example.where2meet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.where2meet.R;
import com.example.where2meet.utils.httpUtils;
import com.example.where2meet.models.Reviews;
import com.example.where2meet.adapters.ReviewsAdapter;
import com.example.where2meet.SearchResult;
import com.example.where2meet.databinding.ActivityDetailBinding;
import com.parse.ParseException;
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
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Place Details");
        String reviewLink = formHostLink(id,"https://api.foursquare.com/v3/places/", "/tips");
        reviewList = new ArrayList<>();
        adapter = new ReviewsAdapter(this, reviewList);
        activityDetailBinding.rvReviews.setAdapter(adapter);
        activityDetailBinding.rvReviews.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
        activityDetailBinding.rvReviews.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

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
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.flBtnInvite){
            Intent i = new Intent(DetailActivity.this, ComposeActivity.class);
            i.putExtra("FormattedAddress", searchResult.getFormattedAddress());
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private String formHostLink(String id, String baseLink, String endLink) {
        return baseLink + id + endLink;
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
            }
        });

    }
}