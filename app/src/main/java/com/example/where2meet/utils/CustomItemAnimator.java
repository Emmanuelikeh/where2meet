package com.example.where2meet.utils;

import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.where2meet.R;

public class CustomItemAnimator extends DefaultItemAnimator {
    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_out));
        return super.animateRemove(holder);
    }
    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        return super.animateAdd(holder);
    }
}
