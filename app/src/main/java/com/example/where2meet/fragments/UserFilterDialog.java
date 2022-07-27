package com.example.where2meet.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.where2meet.R;
import com.example.where2meet.activities.FriendSearchActivity;
import com.example.where2meet.databinding.FragmentUserFilterDialogBinding;
import com.example.where2meet.databinding.FragmentUserFilterDialogBinding;
import com.example.where2meet.utils.ToastUtils;

import java.util.Objects;


public class UserFilterDialog extends DialogFragment {

    private FragmentUserFilterDialogBinding fragmentUserFilterDialogBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_filter_dialog, container, false);
        fragmentUserFilterDialogBinding = FragmentUserFilterDialogBinding.inflate(inflater,container,false);

        fragmentUserFilterDialogBinding.btnCancelFilterDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        fragmentUserFilterDialogBinding.btnSimilarVisited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentUserFilterDialogBinding.btnSimilarVisited.setBackgroundResource(R.drawable.button_border);
            }
        });

        fragmentUserFilterDialogBinding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSelection();
            }
        });

        return fragmentUserFilterDialogBinding.getRoot();
    }

    private void checkSelection() {
        String distance = fragmentUserFilterDialogBinding.tvDistanceValue.getText().toString();
        FriendSearchActivity friendSearchActivity = (FriendSearchActivity) getActivity();
        boolean sameBackgroundResource = Objects.equals(fragmentUserFilterDialogBinding.btnSimilarVisited.getBackground().getConstantState(), getActivity().getResources().getDrawable(R.drawable.button_border).getConstantState());

        if(distance.equals("") && !sameBackgroundResource){
            ToastUtils.presentMessageToUser(getContext(), getString(R.string.select_distance_or_similar));
            return;
        }
        if(!distance.equals("")){
            friendSearchActivity.activityFriendSearchBinding.cvDistance.setVisibility(View.VISIBLE);
            friendSearchActivity.activityFriendSearchBinding.tvFilterInputs.setText(distance);
        }
        if(sameBackgroundResource){
            friendSearchActivity.activityFriendSearchBinding.cvSimilarPlace.setVisibility(View.VISIBLE);
            friendSearchActivity.activityFriendSearchBinding.tvSimilarPlaceInputs.setText(R.string.similar_places_message);
        }
        dismiss();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentUserFilterDialogBinding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fragmentUserFilterDialogBinding.tvDistanceValue.setText("" + progress);
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