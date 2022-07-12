package com.example.where2meet.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.where2meet.R;
import com.example.where2meet.databinding.ItemGroupBinding;
import com.example.where2meet.models.Groups;
import com.parse.ParseFile;

import java.util.List;


public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {
    private Context context;
    private List<Groups> groupList;

    public GroupsAdapter(Context context, List<Groups> groupList){
        this.context = context;
        this.groupList = groupList;
    }

    @NonNull
    @Override
    public GroupsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemGroupBinding itemGroupBinding = ItemGroupBinding.inflate(inflater,parent,false);
        return new ViewHolder(itemGroupBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupsAdapter.ViewHolder holder, int position) {
        Groups group = groupList.get(position);
        holder.bind(group);

    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemGroupBinding itemGroupBinding;
        public ViewHolder(@NonNull ItemGroupBinding itemGroupBinding) {
            super(itemGroupBinding.getRoot());
            this.itemGroupBinding = itemGroupBinding;
        }

        public void bind(Groups group) {
            itemGroupBinding.tvGroupName.setText(group.getGroupName());
            getImage(group);
        }

        private void getImage(Groups group) {
            ParseFile image = group.getImage();
            if(image != null){
                Glide.with(context).load(image.getUrl()).override(200,200).centerCrop().into(itemGroupBinding.ivGroupImage);
            }
            else{
                Glide.with(context).load(R.drawable.ic_baseline_group_24).override(200,200).centerCrop().into(itemGroupBinding.ivGroupImage);
            }

        }
    }
}
