package com.example.where2meet.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.where2meet.R;
import com.example.where2meet.activities.ChatActivity;
import com.example.where2meet.databinding.ItemAcceptedInviteBinding;
import com.example.where2meet.databinding.ItemPendingInviteBinding;
import com.example.where2meet.databinding.ItemRequestedInviteBinding;
import com.example.where2meet.models.Invite;
import com.example.where2meet.models.OnSwipeTouchListener;
import com.example.where2meet.utils.DateUtils;
import com.example.where2meet.utils.GlideUtil;
import com.example.where2meet.utils.ToastUtils;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.ViewHolder> {
    private Context context;
    private List<Invite> inviteList;
    private ScreenTypes screen;
    public enum ScreenTypes {
        ACCEPTEDINVITE,
        SENTINVITE,
        PENDINGINVITE
    }
    public InviteAdapter(Context context, List<Invite> inviteList, ScreenTypes screenTypes){
        this.context = context;
        this.inviteList = inviteList;
        screen = screenTypes;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(screen == ScreenTypes.ACCEPTEDINVITE){
            ItemAcceptedInviteBinding itemAcceptedInviteBinding = ItemAcceptedInviteBinding.inflate(inflater,parent,false);
            return new AcceptedInviteViewHolder(itemAcceptedInviteBinding);
        }
        if(screen == ScreenTypes.PENDINGINVITE){
            ItemPendingInviteBinding itemPendingInviteBinding = ItemPendingInviteBinding.inflate(inflater,parent,false);
            return new PendingInviteViewHolder(itemPendingInviteBinding);
        }
        if(screen == ScreenTypes.SENTINVITE){
            ItemRequestedInviteBinding itemRequestedInviteBinding = ItemRequestedInviteBinding.inflate(inflater,parent,false);
            return  new SentInviteViewHolder(itemRequestedInviteBinding);
        }
        return null;
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

    public abstract class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public abstract void bind(Invite invite);
    }

    public class AcceptedInviteViewHolder extends ViewHolder{
        private ItemAcceptedInviteBinding itemAcceptedInviteBinding;
        public AcceptedInviteViewHolder(@NonNull ItemAcceptedInviteBinding itemAcceptedInviteBinding) {
            super(itemAcceptedInviteBinding.getRoot());
            this.itemAcceptedInviteBinding = itemAcceptedInviteBinding;
        }
        @Override
        public void bind(Invite invite) {
            itemAcceptedInviteBinding.tvAcceptedInviteTitle.setText(invite.getTitle());
            itemAcceptedInviteBinding.tvAcceptedInviteAddress.setText(invite.getAddress());
            DateUtils.setDate("EEE, MMM d, yyyy 'at' hh:mm a",itemAcceptedInviteBinding.tvAcceptedInviteDate,null,invite.getInvitationDate());
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
        }
        private boolean inviteNotFromCurrentUser(Invite invite){
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
                        ToastUtils.presentMessageToUser(context,"Failed: " + e);
                    }
                }
            });
        }
    }

    public class PendingInviteViewHolder extends ViewHolder{
        private ItemPendingInviteBinding itemPendingInviteBinding;
        public PendingInviteViewHolder(@NonNull ItemPendingInviteBinding itemPendingInviteBinding) {
            super(itemPendingInviteBinding.getRoot());
            this.itemPendingInviteBinding = itemPendingInviteBinding;
        }
        @Override
        public void bind(Invite invite) {
            itemPendingInviteBinding.tvPendingInviteTitle.setText(invite.getTitle());
            itemPendingInviteBinding.tvPendingInviteSendersName.setText(invite.getSender().getUsername());
            itemPendingInviteBinding.tvPendingUsersAddress.setText(invite.getAddress());
            DateUtils.setDate("EEE MMM d hh:mm z yyyy",itemPendingInviteBinding.tvPendingInviteInvitationDate,null, invite.getInvitationDate());
            ParseFile image = invite.getSender().getParseFile("profileImage");
            GlideUtil.getImage(80,80, itemPendingInviteBinding.ivPendingInviteSendersProfileImage, image,context);
            itemPendingInviteBinding.btnInviteReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {rejectInvite(invite);
                }
            });
            itemPendingInviteBinding.btnInviteAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {acceptInvite(invite);
                }
            });
            itemView.setOnTouchListener(new OnSwipeTouchListener(context){
                @Override
                public void onSwipeRight() {
                    super.onSwipeRight();
                    acceptInvite(invite);
                }
                @Override
                public void onSwipeLeft() {
                    super.onSwipeLeft();
                    rejectInvite(invite);
                }
            });
        }
        private void acceptInvite(Invite invite){
            invite.setFlag(2);
            invite.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e!= null){
                        ToastUtils.presentMessageToUser(itemView.getContext(), context.getString(R.string.error_saving));
                    }
                    else{
                        int pos = getAdapterPosition();
                        inviteList.remove(pos);
                        notifyItemRemoved(pos);
                    }
                }
            });
        }
        private void rejectInvite(Invite invite) {
            invite.setFlag(1);
            invite.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        int pos = getAdapterPosition();
                        inviteList.remove(pos);
                        notifyItemRemoved(pos);
                    }
                }
            });
        }
    }
    public class SentInviteViewHolder extends ViewHolder{
        ItemRequestedInviteBinding itemRequestedInviteBinding;
        public SentInviteViewHolder(@NonNull ItemRequestedInviteBinding itemRequestedInviteBinding) {
            super(itemRequestedInviteBinding.getRoot());
            this.itemRequestedInviteBinding = itemRequestedInviteBinding;
        }
        @Override
        public void bind(Invite invite) {
            itemRequestedInviteBinding.tvRequestedInviteTitle.setText(invite.getTitle());
            DateUtils.setDate("EEE, MMM d, yyyy 'at' hh:mm a",itemRequestedInviteBinding.tvRequestedInviteDate,"For: ", invite.getInvitationDate());
            DateUtils.setDate("EEE, MMM d, yyyy 'at' hh:mm a",  itemRequestedInviteBinding.tvInviteRequestComposeDate,"Sent at: ",invite.getCreatedAt());
            itemRequestedInviteBinding.tvRequestedInviteeName.setText(invite.getReceiver().getUsername());
            ParseFile image = invite.getReceiver().getParseFile("profileImage");
            GlideUtil.getImage(80,80, itemRequestedInviteBinding.ivRequestsentUserImage,image,context);
            getStatus(invite);
        }
        private void getStatus(Invite invite) {
            int flag = invite.getFlag();
            if(flag == 0){itemRequestedInviteBinding.tvInviteStatus.setText(R.string.pending_status);}
            if(flag == 1){itemRequestedInviteBinding.tvInviteStatus.setText(R.string.rejected_status);}
            if(flag == 2){itemRequestedInviteBinding.tvInviteStatus.setText(R.string.accepted_status);}
        }
    }

    public void clear() {
        inviteList.clear();
        notifyDataSetChanged();
    }
}
