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

import com.example.where2meet.adapters.AcceptedInviteAdapter;
import com.example.where2meet.databinding.FragmentCalendarBinding;
import com.example.where2meet.models.Invite;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarFragment extends Fragment {
    public FragmentCalendarBinding fragmentCalendarBinding;
    protected List<Invite> inviteList;
    protected AcceptedInviteAdapter adapter;
    private List<Calendar> upComingEventsList = new ArrayList<>();

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCalendarBinding = FragmentCalendarBinding.inflate(inflater,container,false);
        View view = fragmentCalendarBinding.getRoot();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentCalendarBinding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inviteList = new ArrayList<>();
        adapter = new AcceptedInviteAdapter(getContext(),inviteList);
        fragmentCalendarBinding.rvUpcomingEvents.setAdapter(adapter);
        fragmentCalendarBinding.rvUpcomingEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        queryInvite();
    }

    private void updateCalender() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.JULY, 24);
        upComingEventsList.add(calendar);
        fragmentCalendarBinding.cvUpcomingevents.setHighlightedDays(upComingEventsList);
    }


    private void refreshView(){
        fragmentCalendarBinding.cvUpcomingevents.setVisibility(View.GONE);
        fragmentCalendarBinding.cvUpcomingevents.setVisibility(View.VISIBLE);
    }

    private void queryInvite() {
        ParseQuery<Invite> sender = ParseQuery.getQuery(Invite.class);
        sender.whereEqualTo(Invite.KEY_RECEIVER,ParseUser.getCurrentUser());

        ParseQuery<Invite> receiver = ParseQuery.getQuery(Invite.class);
        receiver.whereEqualTo(Invite.KEY_SENDER,ParseUser.getCurrentUser());

        List<ParseQuery<Invite>>queries = new ArrayList<ParseQuery<Invite>>();
        queries.add(sender);
        queries.add(receiver);

        ParseQuery<Invite> query = ParseQuery.getQuery(Invite.class);
        query.include(Invite.KEY_SENDER);
        query.include(Invite.KEY_RECEIVER);
        query.or(queries);
        query.whereEqualTo(Invite.KEY_FLAG, 2);
        query.addAscendingOrder(Invite.KEY_INVITATION_DATE);
        query.findInBackground(new FindCallback<Invite>() {
            @Override
            public void done(List<Invite> objects, ParseException e) {
                if(e != null){
                    return;
                }
                inviteList.addAll(objects);
                adapter.notifyDataSetChanged();
                updateCalender();
                refreshView();
            }
        });



    }
}