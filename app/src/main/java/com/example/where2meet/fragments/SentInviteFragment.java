package com.example.where2meet.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.where2meet.R;
import com.example.where2meet.adapters.SentInviteAdapter;
import com.example.where2meet.databinding.FragmentPendingInviteBinding;
import com.example.where2meet.databinding.FragmentSentInviteBinding;
import com.example.where2meet.models.Invite;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class SentInviteFragment extends Fragment {

    private FragmentSentInviteBinding fragmentSentInviteBinding;
    protected List<Invite> inviteList;
    protected SentInviteAdapter adapter;

    public SentInviteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSentInviteBinding = FragmentSentInviteBinding.inflate(inflater,container,false);
        return fragmentSentInviteBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inviteList = new ArrayList<>();
        adapter= new SentInviteAdapter(getContext(), inviteList);
        fragmentSentInviteBinding.rvRequestedInvites.setAdapter(adapter);
        fragmentSentInviteBinding.rvRequestedInvites.setLayoutManager(new LinearLayoutManager(getContext()));
        querySentInvite();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentSentInviteBinding= null;
    }

    private void querySentInvite() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Invite> query = ParseQuery.getQuery(Invite.class);
        // include data referred by user key
        query.include(Invite.KEY_RECEIVER);
        query.whereEqualTo(Invite.KEY_SENDER, ParseUser.getCurrentUser());
//        query.whereNotEqualTo(Invite.KEY_FLAG,true);
        query.findInBackground(new FindCallback<Invite>() {
            @Override
            public void done(List<Invite> objects, ParseException e) {
                if(e != null){
                    Toast.makeText(getContext(),getString(R.string.error_info), Toast.LENGTH_SHORT).show();
                    return;
                }
                inviteList.addAll(objects);
                adapter.notifyDataSetChanged();
            }
        });

    }
}