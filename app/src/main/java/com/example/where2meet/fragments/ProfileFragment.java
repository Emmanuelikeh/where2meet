package com.example.where2meet.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.where2meet.adapters.ViewPagerAdapter;
import com.example.where2meet.R;
import com.example.where2meet.databinding.FragmentProfileBinding;
import com.example.where2meet.utils.GlideUtil;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.util.Calendar;


public class ProfileFragment extends Fragment {
    private FragmentProfileBinding fragmentProfileBinding;
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
        getCurrentUserInformation();
        addFragment();
        fragmentProfileBinding.viewPager.setPagingEnabled(false);
    }
    private void addFragment() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PendingInviteFragment(),getString(R.string.invite_Sent_to_you));
        adapter.addFragment(new SentInviteFragment(), getString(R.string.sent_invite_status));
        fragmentProfileBinding.viewPager.setAdapter(adapter);
        fragmentProfileBinding.tabLayout.setupWithViewPager(fragmentProfileBinding.viewPager);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentProfileBinding = null;
    }
    public void getCurrentUserInformation(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseFile image = currentUser.getParseFile("profileImage");
        fragmentProfileBinding.tvUsersLocation.setText(ParseUser.getCurrentUser().getEmail());
        fragmentProfileBinding.tvCurrentUsersName.setText(currentUser.getUsername());
        GlideUtil.getImage(96,96, fragmentProfileBinding.ivCurrentUserProfileImage, image,getContext());
    }



}