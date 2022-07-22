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
import com.example.where2meet.adapters.PendingInviteAdapter;
import com.example.where2meet.databinding.FragmentPendingInviteBinding;
import com.example.where2meet.models.Invite;
import com.example.where2meet.utils.CustomItemAnimator;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class PendingInviteFragment extends Fragment {
    private FragmentPendingInviteBinding fragmentPendingInviteBinding;
    protected List<Invite> inviteList;
    protected PendingInviteAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentPendingInviteBinding = FragmentPendingInviteBinding.inflate(inflater,container,false);
        return fragmentPendingInviteBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inviteList = new ArrayList<>();
        adapter = new PendingInviteAdapter(getContext(),inviteList);
        fragmentPendingInviteBinding.rvPendingInvities.setAdapter(adapter);
        fragmentPendingInviteBinding.rvPendingInvities.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentPendingInviteBinding.rvPendingInvities.setItemAnimator(new CustomItemAnimator());
        queryInvite();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentPendingInviteBinding = null;
    }

    private void queryInvite() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Invite> query = ParseQuery.getQuery(Invite.class);
        // include data referred by user key
        query.include(Invite.KEY_SENDER);
        query.whereEqualTo(Invite.KEY_RECEIVER, ParseUser.getCurrentUser());
        query.whereEqualTo(Invite.KEY_FLAG,0);
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