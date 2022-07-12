package com.example.where2meet.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.where2meet.SearchResult;
import com.example.where2meet.adapters.SearchResultAdapter;
import com.example.where2meet.databinding.FragmentSearchBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


public class SearchFragment extends Fragment {
    private FragmentSearchBinding fragmentSearchBinding;
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
    }

    public void querySearchResults(String query, String hostLink){
        int limit = 10;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHeaders headers = new RequestHeaders();
        RequestParams params = new RequestParams();
        params.put("query", query);
        params.put("limit",limit);
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

    private void performSearch(){
        String searchquery = fragmentSearchBinding.etSearchBox.getText().toString();
        fragmentSearchBinding.etSearchBox.setText("");
        adapter.clear();
        querySearchResults(searchquery, "https://api.foursquare.com/v3/places/search");
    }


}