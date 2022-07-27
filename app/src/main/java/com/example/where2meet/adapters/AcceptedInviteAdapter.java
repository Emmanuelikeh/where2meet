package com.example.where2meet.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.where2meet.models.OnSwipeTouchListener;
import com.example.where2meet.R;
import com.example.where2meet.activities.ChatActivity;
import com.example.where2meet.databinding.ItemAcceptedInviteBinding;
import com.example.where2meet.models.Invite;
import com.example.where2meet.utils.GlideUtil;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AcceptedInviteAdapter extends RecyclerView.Adapter<AcceptedInviteAdapter.ViewHolder> {
    private Context context;
    private List<Invite> inviteList;

    public AcceptedInviteAdapter(Context context, List<Invite> inviteList){
        this.context = context;
        this.inviteList = inviteList;
    }

    @NonNull
    @Override
    public AcceptedInviteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAcceptedInviteBinding itemAcceptedInviteBinding = ItemAcceptedInviteBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(itemAcceptedInviteBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptedInviteAdapter.ViewHolder holder, int position) {
        Invite invite = inviteList.get(position);
        holder.bind(invite);
    }

    @Override
    public int getItemCount() {
        return inviteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemAcceptedInviteBinding itemAcceptedInviteBinding;
        public ViewHolder(@NonNull ItemAcceptedInviteBinding itemAcceptedInviteBinding) {
            super(itemAcceptedInviteBinding.getRoot());
            this.itemAcceptedInviteBinding = itemAcceptedInviteBinding;
        }

        public void bind(Invite invite) {
            itemAcceptedInviteBinding.tvAcceptedInviteTitle.setText(invite.getTitle());
            itemAcceptedInviteBinding.tvAcceptedInviteAddress.setText(invite.getAddress());
            Date inviteDate = invite.getInvitationDate();
            DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy 'at' hh:mm a", Locale.getDefault());
            String strDate = dateFormat.format(inviteDate);
            itemAcceptedInviteBinding.tvAcceptedInviteDate.setText(strDate);

            if(inviteNotFromCurrentUser(invite)){
                itemAcceptedInviteBinding.tvAccptedInviteName.setText(invite.getSender().getUsername());
                ParseFile image = invite.getSender().getParseFile("profileImage");
                GlideUtil.getImage(80,80, itemAcceptedInviteBinding.ivAcceptedInviteFreindProfileImage, image,context);
            }
            else{
                itemAcceptedInviteBinding.tvAccptedInviteName.setText("You sent an invite to: " + invite.getReceiver().getUsername());
                ParseFile image = invite.getReceiver().getParseFile("profileImage");
                GlideUtil.getImage(80,80, itemAcceptedInviteBinding.ivAcceptedInviteFreindProfileImage, image,context);
            }
            itemAcceptedInviteBinding.btnInviteChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openChatActivity(invite);
                }
            });
            queryUpdatedVisited(invite);
            itemView.setOnTouchListener(new OnSwipeTouchListener(context){
                @Override
                public void onSwipeRight() {
                    super.onSwipeRight();
                    Toast.makeText(context, "right", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onSwipeLeft() {
                    super.onSwipeLeft();
                    Toast.makeText(context, "left", Toast.LENGTH_SHORT).show();
                }
            });
        }
        public boolean inviteNotFromCurrentUser(Invite invite){
            return !Objects.equals(invite.getSender().getUsername(), ParseUser.getCurrentUser().getUsername());
        }
        private void openChatActivity(Invite invite) {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("inviteInfo",invite);
            context.startActivity(intent);
        }
        private void queryUpdatedVisited(Invite invite) {
            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.addAllUnique("Visited", Arrays.asList(invite.getAddress()));
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e != null){
                        Toast.makeText(context, "Failed: " + e,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }




}
