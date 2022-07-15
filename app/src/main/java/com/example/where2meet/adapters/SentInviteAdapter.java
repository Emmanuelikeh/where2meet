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
import com.example.where2meet.databinding.ItemRequestedInviteBinding;
import com.example.where2meet.models.Invite;
import com.parse.ParseFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SentInviteAdapter extends RecyclerView.Adapter<SentInviteAdapter.ViewHolder> {
    private Context context;
    private List<Invite> inviteList;

    public SentInviteAdapter(Context context, List<Invite> inviteList){
        this.context = context;
        this.inviteList = inviteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRequestedInviteBinding itemRequestedInviteBinding = ItemRequestedInviteBinding.inflate(inflater,parent,false);
        return  new ViewHolder(itemRequestedInviteBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Invite invite = inviteList.get(position);
        holder.bind(invite);
    }

    @Override
    public int getItemCount() {
        return inviteList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        private ItemRequestedInviteBinding itemRequestedInviteBinding;
        public ViewHolder(@NonNull ItemRequestedInviteBinding itemRequestedInviteBinding) {
            super(itemRequestedInviteBinding.getRoot());
            this.itemRequestedInviteBinding = itemRequestedInviteBinding;
        }

        public void bind(Invite invite) {
            itemRequestedInviteBinding.tvRequestedInviteTitle.setText(invite.getTitle());
            Date inviteDate = invite.getInvitationDate();
            itemRequestedInviteBinding.tvRequestedInviteDate.setText("For: " + getDate(inviteDate));
            Date createdAt = invite.getCreatedAt();
            itemRequestedInviteBinding.tvInviteRequestComposeDate.setText("Sent at: "+ getDate(createdAt));
            itemRequestedInviteBinding.tvRequestedInviteeName.setText(invite.getReceiver().getUsername());
            ParseFile image = invite.getReceiver().getParseFile("profileImage");
            getImage(image);
            getStatus(invite);
        }

        private void getStatus(Invite invite) {
            int flag = invite.getFlag();
            if(flag == 0){itemRequestedInviteBinding.tvInviteStatus.setText(R.string.pending_status);}
            if(flag == 1){itemRequestedInviteBinding.tvInviteStatus.setText(R.string.rejected_status);}
            if(flag == 2){itemRequestedInviteBinding.tvInviteStatus.setText(R.string.accepted_status);}
        }

        private String getDate(Date date ) {
            DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy 'at' hh:mm a", Locale.getDefault());
            return dateFormat.format(date);
        }

        private void getImage(ParseFile image) {
            if(image == null){
                Glide.with(context).load(R.drawable.ic_baseline_person_24).override(100,200).centerCrop().into(itemRequestedInviteBinding.ivRequestsentUserImage);
            }
            else{
                Glide.with(context).load(image.getUrl()).override(100,200).centerCrop().into(itemRequestedInviteBinding.ivRequestsentUserImage);
            }
        }
    }


}
