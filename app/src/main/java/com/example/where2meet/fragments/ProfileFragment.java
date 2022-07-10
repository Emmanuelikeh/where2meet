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

import com.bumptech.glide.Glide;
import com.example.where2meet.models.Invite;
import com.example.where2meet.adapters.PendingInviteAdapter;
import com.example.where2meet.R;
import com.example.where2meet.databinding.FragmentProfileBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {
    private FragmentProfileBinding fragmentProfileBinding;
    protected List<Invite>inviteList;
    protected PendingInviteAdapter adapter;



    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater,container,false);
        View view = fragmentProfileBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inviteList = new ArrayList<>();
        adapter = new PendingInviteAdapter(getContext(),inviteList);
        fragmentProfileBinding.rvPendingInvites.setAdapter(adapter);
        fragmentProfileBinding.rvPendingInvites.setLayoutManager(new LinearLayoutManager(getContext()));
        getCurrentUserInformation();
        queryInvite();
        getlocationfromactivity();

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentProfileBinding = null;
    }

    private void queryInvite() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Invite> query = ParseQuery.getQuery(Invite.class);
        // include data referred by user key
        query.include(Invite.KEY_SENDER);
        query.whereEqualTo(Invite.KEY_RECEIVER,ParseUser.getCurrentUser());
        query.whereNotEqualTo(Invite.KEY_FLAG,true);
        query.findInBackground(new FindCallback<Invite>() {
            @Override
            public void done(List<Invite> objects, ParseException e) {
                if(e != null){
                    Log.e("check this ", "Issue with getting posts", e);
                    return;
                }
                inviteList.addAll(objects);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void getlocationfromactivity(){
        Bundle args = getArguments();
       if (args != null){
            String location = args.getString("currentLocation");
            fragmentProfileBinding.tvUsersLocation.setText(location);
        }

    }

    public void getCurrentUserInformation(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseFile image = currentUser.getParseFile("profileImage");
        fragmentProfileBinding.tvCurrentUsersName.setText(currentUser.getUsername());
        if(image == null){
            Glide.with(getContext()).load(R.drawable.ic_baseline_person_24).override(100,200).centerCrop().into(fragmentProfileBinding.ivCurrentUserProfileImage);
        }
        else{
            Glide.with(getContext()).load(image.getUrl()).override(100,200).centerCrop().into(fragmentProfileBinding.ivCurrentUserProfileImage);
        }

    }



}