package com.example.where2meet.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.where2meet.R;
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


    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCalendarBinding = FragmentCalendarBinding.inflate(inflater,container,false);
        return fragmentCalendarBinding.getRoot();
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
        fragmentCalendarBinding.cvUpcomingevents.setOnDayClickListener(new OnDayClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDayClick(EventDay eventDay) {
                if(eventDay.getImageDrawable() != null){
                    Toast.makeText(getContext(), "events on this day", Toast.LENGTH_SHORT).show();
                    scrollToPosition(eventDay);
                }
            }
        });
        queryInvite();
    }

    private void scrollToPosition(EventDay eventDay) {
        int position;
        Calendar calendar = eventDay.getCalendar();
        for(int i = 0; i < inviteList.size(); i++){
            Calendar inviteCalendar = Calendar.getInstance();
            inviteCalendar.setTime(inviteList.get(i).getInvitationDate());
            boolean sameDay = isSameDay(calendar, inviteCalendar);
            if(sameDay){
                position = i;
                fragmentCalendarBinding.rvUpcomingEvents.scrollToPosition(position);
                break;
            }
        }
    }
    public static boolean isSameDay(final Calendar cal1, final Calendar cal2) {
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    private void updateCalender(List<Invite> inviteList) {
        List<Calendar> upComingEventsList = new ArrayList<>();
        List<EventDay> events = new ArrayList<>();
        for (Invite invite: inviteList) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(invite.getInvitationDate());
            upComingEventsList.add(calendar);
            events.add(new EventDay(calendar, R.drawable.three_dots));
        }
        fragmentCalendarBinding.cvUpcomingevents.setEvents(events);
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
                updateCalender(inviteList);
                refreshView();
                adapter.notifyDataSetChanged();
            }
        });
    }
}