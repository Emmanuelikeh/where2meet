package com.example.where2meet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.where2meet.R;
import com.example.where2meet.adapters.FriendResultAdapter;
import com.example.where2meet.databinding.ActivityFriendSearchBinding;
import com.example.where2meet.fragments.UserFilterDialog;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FriendSearchActivity extends AppCompatActivity {

    public ActivityFriendSearchBinding activityFriendSearchBinding;
    private FriendResultAdapter adapter;
    private List<ParseUser> parseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFriendSearchBinding = ActivityFriendSearchBinding.inflate(getLayoutInflater());
        setContentView(activityFriendSearchBinding.getRoot());
        parseUsers = new ArrayList<>();
        adapter = new FriendResultAdapter(this, parseUsers);
        activityFriendSearchBinding.rvFriendList.setAdapter(adapter);
        activityFriendSearchBinding.rvFriendList.setLayoutManager(new LinearLayoutManager(this));
        //first call to return a list of all friends
        queryUser("");
        activityFriendSearchBinding.btnFriendSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    performFriendSearch();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        activityFriendSearchBinding.btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserFilterDialog filterDialog = new UserFilterDialog();
                filterDialog.show(getSupportFragmentManager(),"checkthis");
            }
        });
        activityFriendSearchBinding.imgBtnDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityFriendSearchBinding.cvDistance.setVisibility(View.GONE);
                activityFriendSearchBinding.tvFilterInputs.setText("");
            }
        });
        activityFriendSearchBinding.imgBtnSimilarPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityFriendSearchBinding.cvSimilarPlace.setVisibility(View.GONE);
                activityFriendSearchBinding.tvSimilarPlaceInputs.setText("");
            }
        });
    }

    private void queryUser(String friend) {
        String currentUser = ParseUser.getCurrentUser().getUsername();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereContains("username", friend);
        query.whereNotEqualTo("username", currentUser);

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null){
                    parseUsers.addAll(objects);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    private void performFriendSearch() throws JSONException {
        String friendLook =  activityFriendSearchBinding.etFriendSearchBox.getText().toString();
        adapter.clear();
        querySearch(friendLook);
    }
    private void querySearch(String friend) throws JSONException {
        String currentUser = ParseUser.getCurrentUser().getUsername();
        String kilometre = activityFriendSearchBinding.tvFilterInputs.getText().toString();
        String search = activityFriendSearchBinding.tvSimilarPlaceInputs.getText().toString();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereContains("username", friend);
        query.whereNotEqualTo("username", currentUser);

        ParseGeoPoint geoPoint = getCurrentUsersLocation(ParseUser.getCurrentUser());
        if(geoPoint != null && !kilometre.equals("")){
            double km = Double.parseDouble(kilometre);
            query.whereWithinKilometers("LastRecordedLocation",geoPoint,km);
        }
        if(!search.equals("")){
            ParseUser user = ParseUser.getCurrentUser();
            JSONArray visitedJsonArray =   user.getJSONArray("Visited");
            if(visitedJsonArray != null){
                ArrayList<String> visitedArray  = convertToArray(visitedJsonArray);
                query.whereContainedIn("Visited", visitedArray);
            }
        }
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null){
                    parseUsers.addAll(objects);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    private ArrayList<String> convertToArray(JSONArray visitedJsonArray) throws JSONException {
        ArrayList<String> listdata = new ArrayList<String>();
        for(int i=0; i< visitedJsonArray.length(); i++){
            listdata.add(visitedJsonArray.getString(i));
        }
        return listdata;
    }
    public ParseGeoPoint getCurrentUsersLocation(ParseUser user){
              return user.getParseGeoPoint("LastRecordedLocation");
        }
}





