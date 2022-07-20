package com.example.where2meet.fragments;

import android.location.Location;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.where2meet.SearchResult;
import com.example.where2meet.activities.MainActivity;
import com.example.where2meet.adapters.SearchResultAdapter;
import com.example.where2meet.databinding.FragmentSearchBinding;
import com.example.where2meet.utils.CategoriesDictionary;
import com.example.where2meet.utils.PriceTagDictionary;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import okhttp3.Headers;


public class SearchFragment extends Fragment {
    public FragmentSearchBinding fragmentSearchBinding;
    private String TAG = "SearchFragment";
    protected List<SearchResult> searchResultList;
    protected SearchResultAdapter adapter;
    public SearchFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = fragmentSearchBinding.getRoot();
        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentSearchBinding = null;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchResultList = new ArrayList<>();
        adapter = new SearchResultAdapter(getContext(), searchResultList);
        fragmentSearchBinding.rvSearchItems.setAdapter(adapter);
        fragmentSearchBinding.rvSearchItems.setLayoutManager(new LinearLayoutManager(getContext()));
        querySearchResults("", "https://api.foursquare.com/v3/places/search");
        fragmentSearchBinding.imgBtnSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
        fragmentSearchBinding.imgBtnPlaceFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceFilterDialog placeFilterDialog = new PlaceFilterDialog();
                placeFilterDialog.show(getChildFragmentManager(), "placeFilter");
            }
        });
        saveLocation();
    }
    public void querySearchResults(String query, String hostLink){
        int limit = 10;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHeaders headers = new RequestHeaders();
        RequestParams params = new RequestParams();
        params.put("query", query);
        params.put("limit",limit);
        Location userLocal = getUserLocation();
        if(userLocal != null){params.put("ll",getLatitudeAndLongitude(userLocal));}
        if(fragmentSearchBinding.tvDistanceInKm.isShown()){params.put("radius", Integer.parseInt(fragmentSearchBinding.tvDistanceInKm.getText().toString()) * 1000);}
        if(fragmentSearchBinding.tvSortSelection.isShown()){params.put("sort", fragmentSearchBinding.tvSortSelection.getText().toString().toUpperCase());}
        if(fragmentSearchBinding.tvAvailability.isShown()){params.put("open_now",true);}
        if(fragmentSearchBinding.tvCategory.isShown()){params.put("categories", getCategoryIdString(fragmentSearchBinding.tvCategory.getText().toString()));}
        if(fragmentSearchBinding.tvPriceTag.isShown()){params.put("min_price", getPriceTagValue(fragmentSearchBinding.tvPriceTag.getText().toString()));}
        headers.put("Authorization", "fsq3HapICxpWstVvgaSOAlrzGLoCv7IypLnr82Q3c0AVzDk=");
        client.get(hostLink,headers,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    searchResultList.addAll(SearchResult.fromJsonArray(results));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG,"onFailure");
            }
        });
    }
    private String getLatitudeAndLongitude(Location location){
        String longitude = String.valueOf(location.getLongitude());
        String latitude = String.valueOf(location.getLatitude());
        return latitude+","+ longitude;
    }
    private String getPriceTagValue(String priceTag) {
        PriceTagDictionary priceTagDictionary = new PriceTagDictionary();
        Dictionary<String, String> tagDictionary = priceTagDictionary.getPriceTagDictionary();
        return  tagDictionary.get(priceTag);
    }
    private String getCategoryIdString(String category) {
        CategoriesDictionary dictionary = new CategoriesDictionary();
        Dictionary<String,String> categoriesDictionary = dictionary.getCategoriesDictionary();
        return categoriesDictionary.get(category);
    }
    private void performSearch(){
        String searchQuery = fragmentSearchBinding.etSearchBox.getText().toString();
        fragmentSearchBinding.etSearchBox.setText("");
        adapter.clear();
        querySearchResults(searchQuery, "https://api.foursquare.com/v3/places/search");
    }
    private void saveLocation() {
        Location currentUsersLocation = getUserLocation();
        if(currentUsersLocation == null){
            return;
        }
        ParseGeoPoint location = new ParseGeoPoint(currentUsersLocation.getLatitude(),currentUsersLocation.getLongitude());
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.put("LastRecordedLocation",location);
        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public Location getUserLocation(){
        MainActivity activity = (MainActivity) getActivity();
        return  activity.usersLocation;
    }
}