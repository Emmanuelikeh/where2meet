package com.example.where2meet;

import android.util.Log;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class HttpUtils {

    public static void getRequest(String hostLink, JsonHttpResponseHandler handler ){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHeaders headers = new RequestHeaders();
        headers.put("Authorization", "fsq3HapICxpWstVvgaSOAlrzGLoCv7IypLnr82Q3c0AVzDk=");
        client.get(hostLink,headers,null, handler);
}};
