package com.example.where2meet.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.where2meet.R;
import com.example.where2meet.adapters.GroupsAdapter;
import com.example.where2meet.databinding.FragmentChatBinding;
import com.example.where2meet.models.Groups;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {
    private FragmentChatBinding fragmentChatBinding;
    private List<Groups> groupsList;
    private GroupsAdapter adapter;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentChatBinding = FragmentChatBinding.inflate(inflater,container,false);
        return fragmentChatBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        groupsList = new ArrayList<>();
        adapter = new GroupsAdapter(getContext(),groupsList);
        fragmentChatBinding.rvGroups.setAdapter(adapter);
        fragmentChatBinding.rvGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        queryGroups();
    }

    private void queryGroups() {
        ParseQuery<Groups> query = ParseQuery.getQuery(Groups.class);
        ParseUser currentUser = ParseUser.getCurrentUser();
        query.whereEqualTo(Groups.KEY_GROUP_MEMBERS,currentUser.getObjectId());
        query.findInBackground(new FindCallback<Groups>() {
            @Override
            public void done(List<Groups> objects, ParseException e) {
                if (e != null){
                    Toast.makeText(getContext(),getString(R.string.error_info), Toast.LENGTH_SHORT).show();
                    return;
                }
                groupsList.addAll(objects);
                adapter.notifyDataSetChanged();

            }
        });
    }
}