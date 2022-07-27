package com.example.where2meet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.where2meet.models.OnSwipeTouchListener;
import com.example.where2meet.models.Invite;
import com.example.where2meet.R;
import com.example.where2meet.databinding.ItemPendingInviteBinding;
import com.example.where2meet.utils.DateUtils;
import com.example.where2meet.utils.GlideUtil;
import com.example.where2meet.utils.ToastUtils;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PendingInviteAdapter extends RecyclerView.Adapter<PendingInviteAdapter.ViewHolder> {
    private Context context;
    private List<Invite> inviteList;


    public PendingInviteAdapter(Context context, List<Invite> inviteList){
        this.context = context;
        this.inviteList = inviteList;
    }

    @NonNull
    @Override
    public PendingInviteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPendingInviteBinding itemPendingInviteBinding = ItemPendingInviteBinding.inflate(layoutInflater,parent,false);
        return  new ViewHolder(itemPendingInviteBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingInviteAdapter.ViewHolder holder, int position) {
        Invite invite = inviteList.get(position);
        holder.bind(invite);
    }

    @Override
    public int getItemCount() {
        return inviteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemPendingInviteBinding itemPendingInviteBinding;
        public ViewHolder(@NonNull ItemPendingInviteBinding itemPendingInviteBinding) {
            super(itemPendingInviteBinding.getRoot());
            this.itemPendingInviteBinding = itemPendingInviteBinding;
        }
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


}
