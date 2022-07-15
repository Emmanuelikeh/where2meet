package com.example.where2meet.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.where2meet.R;
import com.example.where2meet.activities.FriendSearchActivity;
import com.example.where2meet.databinding.FragmentFilterDialogBinding;

import java.util.Objects;


public class FilterDialog extends DialogFragment {

    private FragmentFilterDialogBinding fragmentFilterDialogBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_filter_dialog, container, false);
        fragmentFilterDialogBinding = FragmentFilterDialogBinding.inflate(inflater,container,false);

        fragmentFilterDialogBinding.btnCancelFilterDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        fragmentFilterDialogBinding.btnSimilarVisited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentFilterDialogBinding.btnSimilarVisited.setBackgroundResource(R.drawable.button_border);
            }
        });

        fragmentFilterDialogBinding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSelection();
            }
        });

        return fragmentFilterDialogBinding.getRoot();
    }

    private void checkSelection() {
        String distance = fragmentFilterDialogBinding.tvDistanceValue.getText().toString();
        FriendSearchActivity friendSearchActivity = (FriendSearchActivity) getActivity();
        boolean sameBackgroundResource = Objects.equals(fragmentFilterDialogBinding.btnSimilarVisited.getBackground().getConstantState(), getActivity().getResources().getDrawable(R.drawable.button_border).getConstantState());

        if(distance.equals("") && !sameBackgroundResource){
            Toast.makeText(getContext(), "please select your distance limits", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!distance.equals("")){
            friendSearchActivity.activityFriendSearchBinding.tvFilterInputs.setText(distance);
        }

        if(sameBackgroundResource){
            friendSearchActivity.activityFriendSearchBinding.tvSimilarPlaceInputs.setText(R.string.similar_places_message);
        }

        dismiss();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentFilterDialogBinding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fragmentFilterDialogBinding.tvDistanceValue.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}